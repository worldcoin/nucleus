import { createHash } from 'node:crypto';
import {
  existsSync,
  mkdirSync,
  readFileSync,
  readdirSync,
  renameSync,
  rmSync,
  writeFileSync,
} from 'node:fs';
import { dirname, resolve } from 'node:path';
import { canonicalSvg } from './normalize-icon.js';
import { svgToVectorDrawable } from '../formats/icons-android.js';

export const FILE_KEY = 'w4tJNJK5PKXQAksXgfnKsM';
export const SECTION_ID = '1181:11691';
export const compare = (a: string, b: string): number =>
  a < b ? -1 : a > b ? 1 : 0;
export const sha256 = (value: string): string =>
  createHash('sha256').update(value).digest('hex');
export const normalizeSvg = (svg: string): string =>
  svg.replace(/\r\n?/g, '\n').trim() + '\n';
export interface IconIdentity {
  name: string;
  variant: 'regular' | 'solid';
  nodeId: string;
}
export interface IconAsset extends IconIdentity {
  svg: string;
  sha256: string;
}
export interface IconBundle {
  schemaVersion: 1;
  fileKey: string;
  sectionId: string;
  version: string;
  inventory: IconIdentity[];
  icons: IconAsset[];
}
export const key = (icon: IconIdentity): string =>
  `${icon.variant}/${icon.name}`;
function identity(value: unknown): IconIdentity {
  const i = value as IconIdentity;
  if (
    !i ||
    typeof i.name !== 'string' ||
    !/^[a-z][a-z0-9]*(?:-[a-z0-9]+)*$/.test(i.name) ||
    !['regular', 'solid'].includes(i.variant) ||
    typeof i.nodeId !== 'string' ||
    !/^\d+:\d+$/.test(i.nodeId)
  ) {
    throw new Error('Invalid icon identity (name, variant, or nodeId)');
  }
  return { name: i.name, variant: i.variant, nodeId: i.nodeId };
}
export function validateInventory(values: unknown[]): IconIdentity[] {
  const inventory = values
    .map(identity)
    .sort((a, c) => compare(key(a), key(c)));
  const expected = new Map<string, IconIdentity>();
  const nodes = new Set<string>();
  const names = new Map<string, string>();
  for (const i of inventory) {
    if (expected.has(key(i)) || nodes.has(i.nodeId))
      throw new Error(`Duplicate identity: ${key(i)}`);
    const symbol = i.name.replace(/-([a-z0-9])/g, (_, c: string) =>
      c.toUpperCase(),
    );
    if (names.has(symbol) && names.get(symbol) !== i.name)
      throw new Error(`Generated symbol collision: ${i.name}`);
    names.set(symbol, i.name);
    nodes.add(i.nodeId);
    expected.set(key(i), i);
  }
  return inventory;
}
export interface Diagnostic {
  severity: 'warning' | 'info';
  code:
    | 'MISSING_VARIANT'
    | 'LOCAL_ONLY_ICON'
    | 'NEW_ICON'
    | 'NAME_MISMATCH'
    | 'NAME_CHANGED'
    | 'DUPLICATE_ARTWORK';
  icon: string;
  variant?: string;
  nodeId?: string;
  previousName?: string;
  members?: string[];
  message: string;
}
export function inventoryDiagnostics(inventory: IconIdentity[]): Diagnostic[] {
  const keys = new Set(inventory.map(key));
  return inventory.flatMap((i) => {
    const variant = i.variant === 'regular' ? 'solid' : 'regular';
    return keys.has(`${variant}/${i.name}`)
      ? []
      : [
          {
            severity: 'warning' as const,
            code: 'MISSING_VARIANT' as const,
            icon: i.name,
            variant,
            message: `Figma has no ${variant} variant for ${i.name}; available variants can still be imported.`,
          },
        ];
  });
}
export function duplicateDiagnostics(icons: IconAsset[]): Diagnostic[] {
  const groups = new Map<string, string[]>();
  for (const icon of icons) {
    const digest = sha256(canonicalSvg(icon.svg));
    const members = groups.get(digest) ?? [];
    members.push(key(icon));
    groups.set(digest, members);
  }
  return [...groups.values()]
    .filter((members) => members.length > 1)
    .map((members) => {
      members.sort(compare);
      return {
        severity: 'warning' as const,
        code: 'DUPLICATE_ARTWORK' as const,
        icon: members[0].split('/')[1],
        members,
        message: `Identical normalized SVG artwork: ${members.join(', ')}. Variants remain separate.`,
      };
    })
    .sort((a, b) => compare(a.members[0], b.members[0]));
}

export function validateBundle(value: unknown): IconBundle {
  const b = value as IconBundle;
  if (
    !b ||
    b.schemaVersion !== 1 ||
    b.fileKey !== FILE_KEY ||
    b.sectionId !== SECTION_ID ||
    typeof b.version !== 'string' ||
    !/^[a-zA-Z0-9_-]+$/.test(b.version) ||
    !Array.isArray(b.inventory) ||
    !b.inventory.length ||
    !Array.isArray(b.icons)
  ) {
    throw new Error(
      'Expected a version-pinned Nucleus icon bundle (schemaVersion 1)',
    );
  }
  const inventory = validateInventory(b.inventory);
  const expected = new Map(inventory.map((i) => [key(i), i]));
  const seen = new Set<string>();
  const icons = b.icons
    .map((raw) => {
      const i = identity(raw);
      if (seen.has(key(i))) throw new Error(`Duplicate SVG: ${key(i)}`);
      seen.add(key(i));
      if (expected.get(key(i))?.nodeId !== i.nodeId)
        throw new Error(`Unexpected SVG: ${key(i)}`);
      if (typeof raw.svg !== 'string' || raw.svg.length > 1_000_000)
        throw new Error(`Invalid SVG: ${key(i)}`);
      const svg = normalizeSvg(raw.svg);
      if (sha256(svg) !== raw.sha256)
        throw new Error(`Checksum mismatch: ${key(i)}`);
      const root = /^<svg\s[^>]*>/.exec(svg)?.[0];
      if (
        !root ||
        !/\bviewBox="0 0 24 24"/.test(root) ||
        !/\bwidth="24"/.test(root) ||
        !/\bheight="24"/.test(root)
      ) {
        throw new Error(`Expected 24x24 SVG: ${key(i)}`);
      }
      svgToVectorDrawable(svg);
      return { ...i, svg, sha256: raw.sha256 };
    })
    .sort((a, c) => compare(key(a), key(c)));
  if (seen.size !== expected.size)
    throw new Error('Incomplete export: SVGs do not cover the inventory');
  return {
    schemaVersion: 1,
    fileKey: FILE_KEY,
    sectionId: SECTION_ID,
    version: b.version,
    inventory,
    icons,
  };
}
export function serializeBundle(value: unknown): string {
  return JSON.stringify(validateBundle(value), null, 2) + '\n';
}
// Each file is replaced atomically; this is not a multi-file filesystem transaction.
export function atomicWrite(path: string, content: string): void {
  mkdirSync(dirname(path), { recursive: true });
  const temporary = `${path}.${process.pid}.tmp`;
  try {
    writeFileSync(temporary, content, { flag: 'wx' });
    renameSync(temporary, path);
  } finally {
    rmSync(temporary, { force: true });
  }
}
export function importBundle(
  value: unknown,
  directory: string,
  write = false,
  strict = false,
) {
  const bundle = validateBundle(value);
  const sourceKeys = new Set(bundle.icons.map(key));
  const retained: string[] = [];
  const localNames = new Set<string>();
  for (const variant of ['regular', 'solid']) {
    const folder = resolve(directory, variant);
    if (!existsSync(folder)) continue;
    for (const name of readdirSync(folder).filter((n) => n.endsWith('.svg'))) {
      const id = `${variant}/${name.slice(0, -4)}`;
      localNames.add(name.slice(0, -4));
      if (!sourceKeys.has(id)) retained.push(id);
    }
  }
  const diagnostics = [
    ...inventoryDiagnostics(bundle.inventory),
    ...duplicateDiagnostics(bundle.icons),
  ];
  const configuredRenames: Array<{ from: string; to: string }> = JSON.parse(
    readFileSync(new URL('./icon-renames.json', import.meta.url), 'utf8'),
  );
  const namingChanges: Array<{ from: string; to: string }> = [];
  const removed: string[] = [];
  for (const rename of configuredRenames) {
    if (
      ![rename.from, rename.to].every(
        (name) =>
          typeof name === 'string' &&
          /^[a-z][a-z0-9]*(?:-[a-z0-9]+)*$/.test(name),
      ) ||
      rename.from === rename.to
    )
      throw new Error('Invalid configured icon rename');
    if (!bundle.icons.some((i) => i.name === rename.to)) continue;
    if (bundle.icons.some((i) => i.name === rename.from))
      throw new Error(
        `Configured retired name still exists in Figma: ${rename.from}`,
      );
    const legacyKeys = retained.filter(
      (id) => id.split('/')[1] === rename.from,
    );
    if (!legacyKeys.length) continue;
    for (const id of legacyKeys) {
      const targetKey = `${id.split('/')[0]}/${rename.to}`;
      if (!sourceKeys.has(targetKey))
        throw new Error(`Rename target variant missing: ${targetKey}`);
      removed.push(id);
      retained.splice(retained.indexOf(id), 1);
    }
    localNames.delete(rename.from);
    namingChanges.push(rename);
    diagnostics.push({
      severity: 'info',
      code: 'NAME_CHANGED',
      icon: rename.to,
      previousName: rename.from,
      message: `Configured naming migration: ${rename.from} → ${rename.to}. Retired source files are removed on write.`,
    });
  }
  const sourceNames = new Set(bundle.inventory.map((i) => i.name));
  for (const name of [...localNames].sort(compare)) {
    if (!sourceNames.has(name))
      diagnostics.push({
        severity: 'warning',
        code: 'LOCAL_ONLY_ICON',
        icon: name,
        message: `${name} exists locally but not in Figma; retained. This may be a rename or removal, not a proven alias.`,
      });
  }
  for (const name of [...sourceNames].sort(compare)) {
    if (!localNames.has(name))
      diagnostics.push({
        severity: 'warning',
        code: 'NEW_ICON',
        icon: name,
        message: `${name} exists in Figma but not locally; check the name before adding it.`,
      });
  }
  const existingManifestPath = resolve(directory, 'figma-manifest.json');
  if (existsSync(existingManifestPath)) {
    const previous = JSON.parse(readFileSync(existingManifestPath, 'utf8'));
    if (!Array.isArray(previous.icons))
      throw new Error('Invalid existing Figma manifest');
    const previousNames = new Map<string, string>(
      previous.icons.map((i: IconIdentity) => [i.nodeId, i.name]),
    );
    for (const i of bundle.inventory) {
      const previousName = previousNames.get(i.nodeId);
      if (previousName && previousName !== i.name) {
        if (
          !namingChanges.some(
            (change) => change.from === previousName && change.to === i.name,
          )
        )
          namingChanges.push({ from: previousName, to: i.name });
        diagnostics.push({
          severity: 'warning',
          code: 'NAME_MISMATCH',
          icon: i.name,
          nodeId: i.nodeId,
          previousName,
          variant: i.variant,
          message: `Figma node ${i.nodeId} changed name from ${previousName} to ${i.name}; no alias is inferred.`,
        });
      }
    }
  }
  namingChanges.sort((a, b) =>
    compare(`${a.from}/${a.to}`, `${b.from}/${b.to}`),
  );
  diagnostics.sort((a, b) =>
    compare(
      `${a.code}/${a.icon}/${a.variant ?? ''}`,
      `${b.code}/${b.icon}/${b.variant ?? ''}`,
    ),
  );
  const hasWarnings = diagnostics.some((d) => d.severity === 'warning');
  const blocked = strict && hasWarnings;
  const symbols = new Map<string, string>();
  for (const id of [...sourceKeys, ...retained]) {
    const name = id.split('/')[1];
    const symbol = name.replace(/-([a-z0-9])/g, (_, c: string) =>
      c.toUpperCase(),
    );
    if (symbols.has(symbol) && symbols.get(symbol) !== name)
      throw new Error(`Generated symbol collision with existing icon: ${name}`);
    symbols.set(symbol, name);
  }
  const added: string[] = [],
    changed: string[] = [],
    unchanged: string[] = [];
  const planned: Array<{ path: string; content: string }> = [];
  const outputs = new Map<string, string>();
  for (const i of bundle.icons) {
    const id = key(i),
      path = resolve(directory, `${id}.svg`);
    const previous = existsSync(path) ? readFileSync(path, 'utf8') : null;
    const normalized = canonicalSvg(i.svg);
    svgToVectorDrawable(normalized);
    const content =
      previous !== null && canonicalSvg(previous) === normalized
        ? previous
        : normalized;
    outputs.set(id, sha256(content));
    if (previous === content) unchanged.push(id);
    else {
      (previous === null ? added : changed).push(id);
      planned.push({ path, content });
    }
  }
  const manifest =
    JSON.stringify(
      {
        schemaVersion: 1,
        fileKey: bundle.fileKey,
        sectionId: bundle.sectionId,
        version: bundle.version,
        bundleSha256: sha256(serializeBundle(bundle)),
        normalization: 'svgo-4.1.0-artboard-v1',
        icons: bundle.icons.map(
          ({ name, variant, nodeId, sha256: digest }) => ({
            name,
            variant,
            nodeId,
            sha256: digest,
            outputSha256: outputs.get(`${variant}/${name}`),
          }),
        ),
      },
      null,
      2,
    ) + '\n';
  const manifestPath = resolve(directory, 'figma-manifest.json');
  const manifestChanged =
    !existsSync(manifestPath) ||
    readFileSync(manifestPath, 'utf8') !== manifest;
  if (manifestChanged) planned.push({ path: manifestPath, content: manifest });
  if (write && !blocked) {
    for (const entry of planned) atomicWrite(entry.path, entry.content);
    for (const id of removed) rmSync(resolve(directory, `${id}.svg`));
  }
  return {
    status: blocked ? 'blocked' : hasWarnings ? 'warning' : 'ok',
    mode: write ? 'write' : 'dry-run',
    applied: write && !blocked,
    strict,
    diagnostics,
    namingChanges,
    removed: removed.sort(compare),
    version: bundle.version,
    exported: bundle.icons.length,
    added,
    changed,
    unchanged,
    retained: retained.sort(compare),
    manifestChanged,
  };
}
