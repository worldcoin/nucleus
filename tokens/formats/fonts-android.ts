import Handlebars from 'handlebars';

import { readTemplate, snakeCase } from './shared.js';
import type { FontToken } from './loaders.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusFonts.kt.hbs'),
);

interface AndroidFontEntry {
  name: string;
  fontResource: string;
  size: string;
  weight: number;
  letterSpacing: string;
  lineHeight: string;
}

function toAndroidEntry(token: FontToken): AndroidFontEntry {
  return {
    name: token.name,
    fontResource: snakeCase(token.family.file),
    size: token.size.toString(),
    weight: token.weight,
    letterSpacing: token.letterSpacing.toString(),
    lineHeight: token.lineHeight.toString(),
  };
}

export function generateAndroidFonts(tokens: FontToken[]): string {
  return TEMPLATE({ packageName: PACKAGE_NAME, tokens: tokens.map(toAndroidEntry) });
}
