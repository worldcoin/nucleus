import { cpSync, existsSync, mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import {
  generateWebIconManifest,
  iconTokenEntries,
  loadIconRegistry,
} from '../formats/icons.js';
import { ROOT, WEB_OUT, logStage } from './shared.js';

export const ICON_SOURCE = 'tokens/definitions/icon/icons.json';
const ICON_SVG_DIR = 'tokens/definitions/icon/svg';

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

/**
 * Emits the web icon manifest for every registered icon token and copies any SVG
 * sources that are present. Missing SVGs are reported, not fatal — run
 * `npm run fetch:icons` (with FIGMA_TOKEN) to populate the full set.
 */
export function buildIcons(): void {
  const registry = loadIconRegistry(ICON_SOURCE);
  const entries = iconTokenEntries(registry);

  const manifestOut = `${WEB_OUT}/nucleus-icons.json`;
  writeOut(manifestOut, generateWebIconManifest(entries, registry.iconSize));

  let copied = 0;
  let missing = 0;
  for (const entry of entries) {
    const src = resolve(ROOT, `${ICON_SVG_DIR}/${entry.name}/${entry.weight}.svg`);
    if (existsSync(src)) {
      const dest = resolve(ROOT, `${WEB_OUT}/${entry.assetPath}`);
      mkdirSync(resolve(dest, '..'), { recursive: true });
      cpSync(src, dest);
      copied += 1;
    } else {
      missing += 1;
    }
  }

  logStage('tokens/definitions/icon', [
    ['web', manifestOut],
    ['web', `${copied} svg copied, ${missing} missing of ${entries.length}`],
  ]);
  if (missing > 0) {
    console.warn(
      `  ⚠ ${missing} icon SVGs missing — run "npm run fetch:icons" (FIGMA_TOKEN) to populate.`,
    );
  }
}
