import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

export interface FontToken {
  name: string;
  fontFamily: string;
  size: number;
  weight: number;
  letterSpacing: number;
  lineHeight: number;
  dynamicTypeStyle?: string;
}

interface RawFontEntry {
  size: number;
  weight: number;
  letterSpacing: number;
  lineHeight: number;
  dynamicTypeStyle?: string;
}

type RawFontDefinitions = Record<string, Record<string, RawFontEntry>>;

export function loadFontTokens(jsonPath: string): FontToken[] {
  const content = JSON.parse(readFileSync(resolve(ROOT, jsonPath), 'utf8')) as RawFontDefinitions;

  const out: FontToken[] = [];
  for (const [fontFamily, entries] of Object.entries(content)) {
    for (const [name, raw] of Object.entries(entries)) {
      out.push({
        name,
        fontFamily,
        size: raw.size,
        weight: raw.weight,
        letterSpacing: raw.letterSpacing,
        lineHeight: raw.lineHeight,
        dynamicTypeStyle: raw.dynamicTypeStyle,
      });
    }
  }
  return out;
}

export function kebabCase(value: string): string {
  return value.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
}

export function snakeCase(value: string): string {
  return value.replace(/([a-z])([A-Z])/g, '$1_$2').toLowerCase();
}
