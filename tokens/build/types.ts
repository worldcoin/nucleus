import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import {
  loadButtonDefinition,
  resolveButtonStyles,
} from '../formats/buttons.js';
import { loadColorTokens, loadFontDefinitions } from '../formats/loaders.js';
import { generateWebTypes } from '../formats/types-web.js';
import { BUTTON_SOURCE } from './buttons.js';
import { ROOT, WEB_OUT, logStage } from './shared.js';

const PRIMITIVE_SOURCE = 'tokens/definitions/color/primitive.json';
const SEMANTIC_LIGHT_SOURCE = 'tokens/definitions/color/semantic.light.json';
const FONT_SOURCE = 'tokens/definitions/font/fonts.json';

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

/**
 * Generate the web package's `index.d.ts` from the token definitions. Light and dark
 * semantic palettes share the same key paths, so the light source is sufficient for
 * the color path set.
 */
export function buildWebTypes(): void {
  const colorTokens = [
    ...loadColorTokens(PRIMITIVE_SOURCE),
    ...loadColorTokens(SEMANTIC_LIGHT_SOURCE),
  ];
  const { tokens: fontTokens } = loadFontDefinitions(FONT_SOURCE);
  const buttonStyleTokens = resolveButtonStyles(
    loadButtonDefinition(BUTTON_SOURCE),
  ).map((style) => style.token);

  const out = `${WEB_OUT}/index.d.ts`;
  writeOut(out, generateWebTypes({ colorTokens, fontTokens, buttonStyleTokens }));

  logStage('token types (web)', [['web', out]]);
}
