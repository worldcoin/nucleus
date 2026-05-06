import { cpSync, mkdirSync, readdirSync, writeFileSync } from 'fs';
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

interface FontFile {
  fileName: string;          // e.g. "WorldProMVP.ttf"
  androidResourceName: string; // e.g. "world_pro_mvp" — lowercase / underscore for res/font/
}

function snakeCaseFileBase(fileName: string): string {
  return fileName
    .replace(/\.[^./]+$/, '')
    .replace(/([a-z0-9])([A-Z])/g, '$1_$2')
    .toLowerCase();
}

function discoverFontFiles(): FontFile[] {
  const dir = resolve(ROOT, FONT_DEFINITIONS_DIR);
  return readdirSync(dir)
    .filter((name) => name.toLowerCase().endsWith('.ttf'))
    .map((fileName) => ({
      fileName,
      androidResourceName: snakeCaseFileBase(fileName),
    }));
}

function copyFile(fromRel: string, toRel: string): void {
  const from = resolve(ROOT, fromRel);
  const to = resolve(ROOT, toRel);
  mkdirSync(resolve(to, '..'), { recursive: true });
  cpSync(from, to);
}

function buildIOSFonts(fontFiles: FontFile[]): void {
  const swiftContent = generateIOSFonts(FONT_SOURCE);

  const swiftDir = resolve(ROOT, IOS_FONTS_OUT);
  mkdirSync(swiftDir, { recursive: true });
  writeFileSync(resolve(swiftDir, 'NucleusFont+Defaults.swift'), swiftContent);

  const copied: string[] = [];
  for (const { fileName } of fontFiles) {
    const dest = `${IOS_FONTS_RESOURCES_OUT}/${fileName}`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (ios)', [
    ['ios', `${IOS_FONTS_OUT}/NucleusFont+Defaults.swift`],
    ...copied.map((path) => ['ios', path] as const),
  ]);
}

function buildAndroidFonts(fontFiles: FontFile[]): void {
  const content = generateAndroidFonts(FONT_SOURCE);

  const tokensDir = resolve(ROOT, ANDROID_TOKENS_OUT);
  mkdirSync(tokensDir, { recursive: true });
  writeFileSync(resolve(tokensDir, 'NucleusFonts.kt'), content);

  const copied: string[] = [];
  for (const { fileName, androidResourceName } of fontFiles) {
    const dest = `${ANDROID_RES_OUT}/font/${androidResourceName}.ttf`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (android)', [
    ['android', `${ANDROID_TOKENS_OUT}/NucleusFonts.kt`],
    ...copied.map((path) => ['android', path] as const),
  ]);
}

function buildWebFonts(fontFiles: FontFile[]): void {
  const { variablesCss, json } = generateWebFonts(FONT_SOURCE);

  const webDir = resolve(ROOT, WEB_OUT);
  mkdirSync(webDir, { recursive: true });
  writeFileSync(resolve(webDir, 'nucleus-fonts.css'), variablesCss);
  writeFileSync(resolve(webDir, 'nucleus-fonts.json'), json);

  const copied: string[] = [];
  for (const { fileName } of fontFiles) {
    const dest = `${WEB_FONTS_OUT}/${fileName}`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${fileName}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (web)', [
    ['web', `${WEB_OUT}/nucleus-fonts.css`],
    ['web', `${WEB_OUT}/nucleus-fonts.json`],
    ...copied.map((path) => ['web', path] as const),
  ]);
}

export async function buildFonts(): Promise<void> {
  const fontFiles = discoverFontFiles();
  buildIOSFonts(fontFiles);
  buildAndroidFonts(fontFiles);
  buildWebFonts(fontFiles);
}
