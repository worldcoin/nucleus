import { mkdirSync, writeFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

import { iconTokenEntries, loadIconRegistry } from '../formats/icons.js';

/**
 * Bulk-export every registered icon as SVG from Figma into
 * `tokens/definitions/icon/svg/{name}/{weight}.svg`.
 *
 * Requires a Figma personal access token:  FIGMA_TOKEN=… npm run fetch:icons
 * Uses the registry's node ids (tokens/definitions/icon/icons.json) and the
 * Figma Images API (https://www.figma.com/developers/api#get-images-endpoint).
 */

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');
const ICON_SOURCE = 'tokens/definitions/icon/icons.json';
const ICON_SVG_DIR = 'tokens/definitions/icon/svg';
const BATCH_SIZE = 50;

interface ImagesResponse {
  images: Record<string, string | null>;
  err?: string | null;
}

async function main(): Promise<void> {
  const token = process.env.FIGMA_TOKEN;
  if (!token) {
    console.error('FIGMA_TOKEN env var is required (a Figma personal access token).');
    process.exit(1);
  }

  const registry = loadIconRegistry(ICON_SOURCE);
  const entries = iconTokenEntries(registry);
  const byNode = new Map(entries.map((entry) => [entry.nodeId, entry]));
  const ids = [...byNode.keys()];

  let written = 0;
  let failed = 0;

  for (let i = 0; i < ids.length; i += BATCH_SIZE) {
    const chunk = ids.slice(i, i + BATCH_SIZE);
    const url =
      `https://api.figma.com/v1/images/${registry.figmaFileKey}` +
      `?ids=${encodeURIComponent(chunk.join(','))}&format=svg`;

    const res = await fetch(url, { headers: { 'X-Figma-Token': token } });
    if (!res.ok) {
      console.error(`Figma images request failed: ${res.status} ${await res.text()}`);
      process.exit(1);
    }
    const data = (await res.json()) as ImagesResponse;
    if (data.err) {
      console.error(`Figma error: ${data.err}`);
      process.exit(1);
    }

    for (const [nodeId, imageUrl] of Object.entries(data.images)) {
      const entry = byNode.get(nodeId);
      if (!entry) continue;
      if (!imageUrl) {
        failed += 1;
        console.warn(`  ⚠ no image returned for ${entry.token} (${nodeId})`);
        continue;
      }
      const svgRes = await fetch(imageUrl);
      if (!svgRes.ok) {
        failed += 1;
        console.warn(`  ⚠ download failed for ${entry.token}`);
        continue;
      }
      const dest = resolve(ROOT, `${ICON_SVG_DIR}/${entry.name}/${entry.weight}.svg`);
      mkdirSync(dirname(dest), { recursive: true });
      writeFileSync(dest, await svgRes.text());
      written += 1;
    }
    console.log(`fetched ${Math.min(i + BATCH_SIZE, ids.length)}/${ids.length}`);
  }

  console.log(`\n✓ wrote ${written} SVGs (${failed} failed) to ${ICON_SVG_DIR}`);
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
