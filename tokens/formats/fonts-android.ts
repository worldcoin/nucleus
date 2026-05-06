import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import { loadFontTokens } from './fonts-shared.js';
import type { FontToken } from './fonts-shared.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusFonts.kt.hbs'),
);

interface AndroidFontEntry {
  name: string;
  fontName: string;
  size: string;
  weight: number;
  letterSpacing: string;
  lineHeight: string;
}

function toAndroidEntry(token: FontToken): AndroidFontEntry {
  return {
    name: token.name,
    fontName: token.fontName,
    size: token.size.toString(),
    weight: token.weight,
    letterSpacing: token.letterSpacing.toString(),
    lineHeight: token.lineHeight.toString(),
  };
}

export function generateAndroidFonts(jsonPath: string): string {
  const tokens = loadFontTokens(jsonPath).map(toAndroidEntry);
  return TEMPLATE({ packageName: PACKAGE_NAME, tokens });
}
