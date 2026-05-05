import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

export interface FontToken {
  name: string;
  fontName: string;
  size: number;
  weight: number;
  letterSpacing: number;
  lineHeight: number;
  dynamicTypeStyle?: string;
}

interface RawFontEntry {
  fontName: string;
  size: number;
  weight: number;
  letterSpacing: number;
  lineHeight: number;
  dynamicTypeStyle?: string;
}

type RawFontDefinitions = Record<string, RawFontEntry>;

export function loadFontTokens(jsonPath: string): FontToken[] {
  const content = JSON.parse(readFileSync(resolve(ROOT, jsonPath), 'utf8')) as RawFontDefinitions;

  return Object.entries(content).map(([name, raw]) => ({
    name,
    fontName: raw.fontName,
    size: raw.size,
    weight: raw.weight,
    letterSpacing: raw.letterSpacing,
    lineHeight: raw.lineHeight,
    dynamicTypeStyle: raw.dynamicTypeStyle,
  }));
}

export function kebabCase(value: string): string {
  return value.replace(/([a-z])([A-Z])/g, '$1-$2').toLowerCase();
}
