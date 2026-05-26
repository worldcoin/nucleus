import { kebabCase } from './shared.js';
import type { FontToken } from './loaders.js';

export interface WebFontResult {
  variablesCss: string;
  json: string;
}

function variableName(token: FontToken, key: string): string {
  return `--nucleus-font-${kebabCase(token.name)}-${key}`;
}

function buildVariablesCss(tokens: FontToken[]): string {
  const lines: string[] = [];
  for (const token of tokens) {
    lines.push(`  ${variableName(token, 'family')}: "${token.family.postscriptName}";`);
    lines.push(`  ${variableName(token, 'size')}: ${token.size}px;`);
    lines.push(`  ${variableName(token, 'weight')}: ${token.weight};`);
    lines.push(`  ${variableName(token, 'letter-spacing')}: ${token.letterSpacing}em;`);
    lines.push(`  ${variableName(token, 'line-height')}: ${token.lineHeight};`);
  }
  return [':root {', ...lines, '}', ''].join('\n');
}

function buildJson(tokens: FontToken[]): string {
  const result: Record<string, Record<string, string | number>> = {};
  for (const token of tokens) {
    result[token.name] = {
      fontName: token.family.postscriptName,
      size: `${token.size}px`,
      weight: token.weight,
      letterSpacing: `${token.letterSpacing}em`,
      lineHeight: token.lineHeight,
    };
  }
  return JSON.stringify(result, null, 2) + '\n';
}

export function generateWebFonts(tokens: FontToken[]): WebFontResult {
  return {
    variablesCss: buildVariablesCss(tokens),
    json: buildJson(tokens),
  };
}
