import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import { loadFontTokens, snakeCase } from './fonts-shared.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusFonts.kt.hbs'),
);

interface AndroidFontEntry {
  name: string;
  size: string;
  weight: number;
  letterSpacing: string;
  lineHeight: string;
}

interface AndroidFontFamily {
  name: string;          // e.g. "WorldPro" — used as the Kotlin object name
  resourceName: string;  // e.g. "world_pro_mvp" — must match res/font/<name>.ttf
  tokens: AndroidFontEntry[];
}

export interface AndroidFontResource {
  fileName: string;       // e.g. "WorldProMVP.ttf"
  resourceName: string;   // e.g. "world_pro_mvp" — file name (without extension) inside res/font/
}

export interface AndroidFontResult {
  content: string;
  resources: AndroidFontResource[];
}

export function generateAndroidFonts(jsonPath: string, fontFiles: Record<string, string>): AndroidFontResult {
  const tokens = loadFontTokens(jsonPath);

  const families = new Map<string, AndroidFontFamily>();
  const resources: AndroidFontResource[] = [];

  for (const token of tokens) {
    let family = families.get(token.fontFamily);
    if (!family) {
      const fileName = fontFiles[token.fontFamily];
      if (!fileName) {
        throw new Error(`No font file mapping for family '${token.fontFamily}'. Add an entry to FONT_FILES in tokens/build/fonts.ts.`);
      }
      const baseName = fileName.replace(/\.[^./]+$/, '');
      const resourceName = snakeCase(baseName);
      family = { name: token.fontFamily, resourceName, tokens: [] };
      families.set(token.fontFamily, family);
      resources.push({ fileName, resourceName });
    }
    family.tokens.push({
      name: token.name,
      size: token.size.toString(),
      weight: token.weight,
      letterSpacing: token.letterSpacing.toString(),
      lineHeight: token.lineHeight.toString(),
    });
  }

  const content = TEMPLATE({
    packageName: PACKAGE_NAME,
    families: Array.from(families.values()),
  });

  return { content, resources };
}
