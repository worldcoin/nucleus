import { cpSync, mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { generateAndroidFonts } from '../formats/fonts-android.js';
import { generateIOSFonts } from '../formats/fonts-ios.js';
import { generateWebFonts } from '../formats/fonts-web.js';
import {
  ANDROID_RES_OUT,
  ANDROID_TOKENS_OUT,
  IOS_FONTS_OUT,
  IOS_FONTS_RESOURCES_OUT,
  ROOT,
  WEB_FONTS_OUT,
  WEB_OUT,
  logStage,
} from './shared.js';

const FONT_SOURCE = 'tokens/definitions/font/fonts.json';
const FONT_DEFINITIONS_DIR = 'tokens/definitions/font';

// One ttf file per nucleus font family. Keep keys aligned with the top-level keys in fonts.json so the family lookups resolve.
const FONT_FILES: Record<string, string> = {
  WorldPro: 'WorldProMVP.ttf',
};

function copyFile(fromRel: string, toRel: string): void {
  const from = resolve(ROOT, fromRel);
  const to = resolve(ROOT, toRel);
  mkdirSync(resolve(to, '..'), { recursive: true });
  cpSync(from, to);
}

function buildIOSFonts(): void {
  const swiftContent = generateIOSFonts(FONT_SOURCE);

  const swiftDir = resolve(ROOT, IOS_FONTS_OUT);
  mkdirSync(swiftDir, { recursive: true });
  writeFileSync(resolve(swiftDir, 'NucleusFont+Defaults.swift'), swiftContent);

  const resourceFiles: string[] = [];
  for (const fileName of Object.values(FONT_FILES)) {
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, `${IOS_FONTS_RESOURCES_OUT}/${fileName}`);
    resourceFiles.push(`${IOS_FONTS_RESOURCES_OUT}/${fileName}`);
  }

  logStage('tokens/definitions/font (ios)', [
    ['ios', `${IOS_FONTS_OUT}/NucleusFont+Defaults.swift`],
    ...resourceFiles.map((path) => ['ios', path] as const),
  ]);
}

function buildAndroidFonts(): void {
  const { content, resources } = generateAndroidFonts(FONT_SOURCE, FONT_FILES);

  const tokensDir = resolve(ROOT, ANDROID_TOKENS_OUT);
  mkdirSync(tokensDir, { recursive: true });
  writeFileSync(resolve(tokensDir, 'NucleusFonts.kt'), content);

  const resourceFiles: string[] = [];
  for (const { fileName, resourceName } of resources) {
    const dest = `${ANDROID_RES_OUT}/font/${resourceName}.ttf`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, dest);
    resourceFiles.push(dest);
  }

  logStage('tokens/definitions/font (android)', [
    ['android', `${ANDROID_TOKENS_OUT}/NucleusFonts.kt`],
    ...resourceFiles.map((path) => ['android', path] as const),
  ]);
}

function buildWebFonts(): void {
  const faces = Object.entries(FONT_FILES).map(([family, fileName]) => ({
    family,
    fileName,
    format: 'truetype',
  }));

  const { variablesCss, fontFacesCss, json } = generateWebFonts(FONT_SOURCE, faces);

  const webDir = resolve(ROOT, WEB_OUT);
  mkdirSync(webDir, { recursive: true });
  writeFileSync(resolve(webDir, 'nucleus-fonts.css'), variablesCss);
  writeFileSync(resolve(webDir, 'nucleus-font-faces.css'), fontFacesCss);
  writeFileSync(resolve(webDir, 'nucleus-fonts.json'), json);

  const resourceFiles: string[] = [];
  for (const fileName of Object.values(FONT_FILES)) {
    const dest = `${WEB_FONTS_OUT}/${fileName}`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, dest);
    resourceFiles.push(dest);
  }

  logStage('tokens/definitions/font (web)', [
    ['web', `${WEB_OUT}/nucleus-fonts.css`],
    ['web', `${WEB_OUT}/nucleus-font-faces.css`],
    ['web', `${WEB_OUT}/nucleus-fonts.json`],
    ...resourceFiles.map((path) => ['web', path] as const),
  ]);
}

export async function buildFonts(): Promise<void> {
  buildIOSFonts();
  buildAndroidFonts();
  buildWebFonts();
}
