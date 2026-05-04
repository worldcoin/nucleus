import { loadFontTokens, kebabCase } from './fonts-shared.js';
import type { FontToken } from './fonts-shared.js';

export interface WebFontFace {
  family: string;
  fileName: string;       // e.g. "WorldProMVP.ttf"
  format: string;         // e.g. "truetype"
}

export interface WebFontResult {
  variablesCss: string;
  fontFacesCss: string;
  json: string;
}

function familyVariableName(family: string): string {
  return `--nucleus-font-family-${kebabCase(family)}`;
}

function tokenVariableName(token: FontToken, key: string): string {
  return `--nucleus-font-${kebabCase(token.fontFamily)}-${kebabCase(token.name)}-${key}`;
}

function buildVariablesCss(tokens: FontToken[]): string {
  const families = new Set(tokens.map((t) => t.fontFamily));
  const lines: string[] = [];

  for (const family of families) {
    lines.push(`  ${familyVariableName(family)}: "${family}";`);
  }
  for (const token of tokens) {
    lines.push(`  ${tokenVariableName(token, 'family')}: var(${familyVariableName(token.fontFamily)});`);
    lines.push(`  ${tokenVariableName(token, 'size')}: ${token.size}px;`);
    lines.push(`  ${tokenVariableName(token, 'weight')}: ${token.weight};`);
    lines.push(`  ${tokenVariableName(token, 'letter-spacing')}: ${token.letterSpacing}em;`);
    lines.push(`  ${tokenVariableName(token, 'line-height')}: ${token.lineHeight};`);
  }

  return [':root {', ...lines, '}', ''].join('\n');
}

function buildFontFacesCss(faces: WebFontFace[]): string {
  const blocks = faces.map((face) => [
    '@font-face {',
    `  font-family: "${face.family}";`,
    `  src: url("./fonts/${face.fileName}") format("${face.format}");`,
    '  font-display: swap;',
    '}',
  ].join('\n'));

  return blocks.join('\n\n') + '\n';
}

function buildJson(tokens: FontToken[]): string {
  const result: Record<string, Record<string, Record<string, string | number>>> = {};
  for (const token of tokens) {
    if (!result[token.fontFamily]) {
      result[token.fontFamily] = {};
    }
    result[token.fontFamily][token.name] = {
      fontFamily: token.fontFamily,
      size: `${token.size}px`,
      weight: token.weight,
      letterSpacing: `${token.letterSpacing}em`,
      lineHeight: token.lineHeight,
    };
  }
  return JSON.stringify(result, null, 2) + '\n';
}

export function generateWebFonts(jsonPath: string, faces: WebFontFace[]): WebFontResult {
  const tokens = loadFontTokens(jsonPath);
  return {
    variablesCss: buildVariablesCss(tokens),
    fontFacesCss: buildFontFacesCss(faces),
    json: buildJson(tokens),
  };
}
