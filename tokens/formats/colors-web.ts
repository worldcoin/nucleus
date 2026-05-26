import {
  camelCasePath,
  kebabCasePath,
  parseReference,
  publicColorPath,
} from './shared.js';
import type { ColorLeaf } from './loaders.js';

function cssVariableName(path: string[]): string {
  return `--nucleus-${kebabCasePath(publicColorPath(path))}`;
}

function cssColorValue(rawValue: string): string {
  const referencePath = parseReference(rawValue);
  if (referencePath) return `var(${cssVariableName(referencePath)})`;
  return rawValue;
}

export function generateWebColorCss(tokens: ColorLeaf[]): string {
  const lines = tokens.map(
    (token) => `  ${cssVariableName(token.path)}: ${cssColorValue(token.rawValue)};`,
  );
  return [':root {', ...lines, '}', ''].join('\n');
}

export function generateWebColorJson(tokens: ColorLeaf[]): string {
  const result: Record<string, string> = {};
  for (const token of tokens) {
    result[camelCasePath(publicColorPath(token.path))] = token.rawValue;
  }
  return JSON.stringify(result, null, 2) + '\n';
}
