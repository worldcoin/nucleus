import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

export interface IconRegistry {
  /** Figma file the icons are sourced from (used by the export script). */
  figmaFileKey: string;
  /** Canvas size of every icon, in px. */
  iconSize: number;
  /** name → weight → Figma node id. */
  icons: Record<string, Record<string, string>>;
}

export interface IconTokenEntry {
  /** Wire token, e.g. `icon.arrow-right.regular`. */
  token: string;
  name: string;
  weight: string;
  nodeId: string;
  /** Published asset path, e.g. `icons/arrow-right/regular.svg`. */
  assetPath: string;
}

export function loadIconRegistry(jsonPath: string): IconRegistry {
  const reg = JSON.parse(
    readFileSync(resolve(ROOT, jsonPath), 'utf8'),
  ) as IconRegistry;
  if (!reg.icons || typeof reg.icons !== 'object') {
    throw new Error(`${jsonPath} must define an "icons" map`);
  }
  return reg;
}

export function iconTokenEntries(reg: IconRegistry): IconTokenEntry[] {
  const out: IconTokenEntry[] = [];
  for (const [name, weights] of Object.entries(reg.icons)) {
    for (const [weight, nodeId] of Object.entries(weights)) {
      out.push({
        token: `icon.${name}.${weight}`,
        name,
        weight,
        nodeId,
        assetPath: `icons/${name}/${weight}.svg`,
      });
    }
  }
  return out;
}

export function generateWebIconManifest(
  entries: IconTokenEntry[],
  iconSize: number,
): string {
  const result: Record<string, { path: string; size: number }> = {};
  for (const entry of entries) {
    result[entry.token] = { path: entry.assetPath, size: iconSize };
  }
  return `${JSON.stringify(result, null, 2)}\n`;
}
