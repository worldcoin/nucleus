import { parseArgs } from 'node:util';
import { pathToFileURL } from 'node:url';
import { resolve } from 'node:path';
import { setTimeout as sleep } from 'node:timers/promises';
import {
  atomicWrite,
  compare,
  FILE_KEY,
  SECTION_ID,
  normalizeSvg,
  serializeBundle,
  sha256,
  validateBundle,
  validateInventory,
  inventoryDiagnostics,
  duplicateDiagnostics,
  type IconIdentity,
  type IconAsset,
} from './icon-bundle.js';

interface FigmaNode {
  id: string;
  name: string;
  type: string;
  children?: FigmaNode[];
}
interface Options {
  fetch?: typeof fetch;
  sleep?: (milliseconds: number) => Promise<unknown>;
  batchSize?: number;
  concurrency?: number;
}
function findNode(node: FigmaNode, id: string): FigmaNode | undefined {
  if (node.id === id) return node;
  for (const child of node.children ?? []) {
    const found = findNode(child, id);
    if (found) return found;
  }
}
export function inventoryFromSection(section: FigmaNode): IconIdentity[] {
  if (section.type !== 'SECTION' || section.id !== SECTION_ID)
    throw new Error('Iconography section is missing');
  const icons: IconIdentity[] = [];
  for (const set of section.children ?? []) {
    if (set.type !== 'COMPONENT_SET' || set.name === 'placeholder') continue;
    if (
      !(set.children ?? []).some(
        (n) =>
          n.type === 'COMPONENT' &&
          ['Weight=Regular', 'Weight=Solid'].includes(n.name),
      )
    )
      throw new Error(`No supported variants for ${set.name}`);
    for (const variant of ['regular', 'solid'] as const) {
      const name = `Weight=${variant === 'regular' ? 'Regular' : 'Solid'}`;
      const nodes = (set.children ?? []).filter(
        (n) => n.type === 'COMPONENT' && n.name === name,
      );
      if (nodes.length > 1)
        throw new Error(`Duplicate ${name} components for ${set.name}`);
      if (nodes.length)
        icons.push({ name: set.name, variant, nodeId: nodes[0].id });
    }
  }
  return icons.sort((a, b) =>
    compare(`${a.variant}/${a.name}`, `${b.variant}/${b.name}`),
  );
}
export async function downloadBundle(
  token: string,
  version?: string,
  options: Options = {},
) {
  if (!token)
    throw new Error(
      'Set FIGMA_ACCESS_TOKEN in the environment (file_content:read scope)',
    );
  const transport = options.fetch ?? fetch,
    pause = options.sleep ?? sleep;
  const batchSize = options.batchSize ?? 40,
    concurrency = options.concurrency ?? 4;
  if (
    !Number.isInteger(batchSize) ||
    batchSize < 1 ||
    batchSize > 100 ||
    !Number.isInteger(concurrency) ||
    concurrency < 1 ||
    concurrency > 8
  )
    throw new Error('Invalid batch size or concurrency');
  // Retry only transient failures. Never forward authentication to SVG asset hosts.
  async function request(url: string, authenticated: boolean): Promise<string> {
    for (let attempt = 0; attempt < 4; attempt++) {
      let response: Response;
      try {
        response = await transport(url, {
          headers: authenticated ? { 'X-Figma-Token': token } : {},
          signal: AbortSignal.timeout(30_000),
        });
      } catch {
        if (attempt === 3)
          throw new Error(
            'Figma request failed after 4 attempts (network or timeout)',
          );
        await pause(1000 * 2 ** attempt);
        continue;
      }
      if (response.ok) return response.text();
      if (response.status !== 429 && response.status < 500)
        throw new Error(`Figma request failed (HTTP ${response.status})`);
      if (attempt === 3)
        throw new Error(
          `Figma request failed after 4 attempts (HTTP ${response.status})`,
        );
      const retryAfter = response.headers.get('retry-after');
      const delay =
        retryAfter === null
          ? 1000 * 2 ** attempt
          : /^\d+(\.\d+)?$/.test(retryAfter)
            ? Number(retryAfter) * 1000
            : Math.max(0, Date.parse(retryAfter) - Date.now());
      if (!Number.isFinite(delay) || delay > 30_000)
        throw new Error(
          'Figma rate limit requires a later retry; export was not saved',
        );
      await response.body?.cancel();
      await pause(delay);
    }
    throw new Error('Unreachable request state');
  }
  const query = new URLSearchParams({ ids: SECTION_ID });
  if (version) query.set('version', version);
  const file = JSON.parse(
    await request(`https://api.figma.com/v1/files/${FILE_KEY}?${query}`, true),
  );
  if (typeof file.version !== 'string' || (version && file.version !== version))
    throw new Error('Figma returned an unexpected file version');
  const section = file.document && findNode(file.document, SECTION_ID);
  if (!section) throw new Error('Iconography section is missing');
  const inventory = inventoryFromSection(section);
  if (!inventory.length) throw new Error('No explicit icon variants found');
  validateInventory(inventory);
  const icons = [];
  for (let start = 0; start < inventory.length; start += batchSize) {
    const batch = inventory.slice(start, start + batchSize);
    const params = new URLSearchParams({
      ids: batch.map((i) => i.nodeId).join(','),
      version: file.version,
      format: 'svg',
      svg_outline_text: 'true',
      svg_include_id: 'false',
      svg_include_node_id: 'false',
      svg_simplify_stroke: 'true',
      contents_only: 'true',
      use_absolute_bounds: 'false',
    });
    const rendered = JSON.parse(
      await request(
        `https://api.figma.com/v1/images/${FILE_KEY}?${params}`,
        true,
      ),
    );
    if (rendered.err)
      throw new Error('Figma could not render the requested SVG batch');
    const downloaded: IconAsset[] = [];
    let next = 0;
    await Promise.all(
      Array.from({ length: Math.min(concurrency, batch.length) }, async () => {
        while (next < batch.length) {
          const index = next++,
            icon = batch[index],
            url = rendered.images?.[icon.nodeId];
          if (typeof url !== 'string' || new URL(url).protocol !== 'https:')
            throw new Error(`No SVG render for ${icon.name}/${icon.variant}`);
          const svg = normalizeSvg(await request(url, false));
          downloaded[index] = { ...icon, svg, sha256: sha256(svg) };
        }
      }),
    );
    icons.push(...downloaded);
  }
  return validateBundle({
    schemaVersion: 1,
    fileKey: FILE_KEY,
    sectionId: SECTION_ID,
    version: file.version,
    inventory,
    icons,
  });
}
async function main() {
  const { values } = parseArgs({
    options: {
      out: { type: 'string' },
      version: { type: 'string' },
      'batch-size': { type: 'string' },
      concurrency: { type: 'string' },
    },
  });
  if (!values.out)
    throw new Error(
      'Usage: npm run icons:download -- --out bundle.json [--version VERSION] [--batch-size 40] [--concurrency 4]',
    );
  const bundle = await downloadBundle(
    process.env.FIGMA_ACCESS_TOKEN ?? '',
    values.version,
    {
      batchSize: values['batch-size']
        ? Number(values['batch-size'])
        : undefined,
      concurrency: values.concurrency ? Number(values.concurrency) : undefined,
    },
  );
  atomicWrite(resolve(values.out), serializeBundle(bundle));
  const diagnostics = [
    ...inventoryDiagnostics(bundle.inventory),
    ...duplicateDiagnostics(bundle.icons),
  ];
  console.log(
    JSON.stringify({
      status: diagnostics.length ? 'warning' : 'ok',
      diagnostics,
      version: bundle.version,
      exported: bundle.icons.length,
      output: resolve(values.out),
    }),
  );
}
if (
  process.argv[1] &&
  import.meta.url === pathToFileURL(resolve(process.argv[1])).href
) {
  main().catch((error) => {
    console.log(
      JSON.stringify(
        {
          status: 'error',
          diagnostics: [
            {
              severity: 'error',
              code: 'DOWNLOAD_FAILED',
              message: error.message,
            },
          ],
        },
        null,
        2,
      ),
    );
    process.exitCode = 1;
  });
}
