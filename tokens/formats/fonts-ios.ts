import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import { loadFontTokens } from './fonts-shared.js';
import type { FontToken } from './fonts-shared.js';

const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusFont+Defaults.swift.hbs'),
);

interface IOSFontEntry {
  name: string;
  fontName: string;
  size: string;
  weight: string;
  letterSpacing: string;
  lineHeight: string;
  dynamicTypeStyle: string;
}

interface IOSFontFamily {
  name: string;
  tokens: IOSFontEntry[];
}

function toIOSEntry(token: FontToken): IOSFontEntry {
  return {
    name: token.name,
    fontName: token.fontFamily,
    size: token.size.toString(),
    weight: token.weight.toString(),
    letterSpacing: token.letterSpacing.toString(),
    lineHeight: token.lineHeight.toString(),
    dynamicTypeStyle: token.dynamicTypeStyle ?? 'body',
  };
}

function groupByFamily(tokens: FontToken[]): IOSFontFamily[] {
  const families = new Map<string, IOSFontEntry[]>();
  for (const token of tokens) {
    const list = families.get(token.fontFamily) ?? [];
    list.push(toIOSEntry(token));
    families.set(token.fontFamily, list);
  }
  return Array.from(families, ([name, tokens]) => ({ name, tokens }));
}

export function generateIOSFonts(jsonPath: string): string {
  const families = groupByFamily(loadFontTokens(jsonPath));
  return TEMPLATE({ families });
}
