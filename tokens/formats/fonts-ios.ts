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

function toIOSEntry(token: FontToken): IOSFontEntry {
  return {
    name: token.name,
    fontName: token.fontName,
    size: token.size.toString(),
    weight: token.weight.toString(),
    letterSpacing: token.letterSpacing.toString(),
    lineHeight: token.lineHeight.toString(),
    dynamicTypeStyle: token.dynamicTypeStyle ?? 'body',
  };
}

export function generateIOSFonts(jsonPath: string): string {
  const tokens = loadFontTokens(jsonPath).map(toIOSEntry);
  return TEMPLATE({ tokens });
}
