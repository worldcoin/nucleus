import { cpSync, mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { generateAndroidFonts } from '../formats/fonts-android.js';
import { generateIOSFonts } from '../formats/fonts-ios.js';
import { generateWebFonts } from '../formats/fonts-web.js';
import { loadFontDefinitions } from '../formats/loaders.js';
import { snakeCase } from '../formats/shared.js';
import type { FontFamily } from '../formats/loaders.js';
import type { FontToken } from '../formats/loaders.js';
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

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

function copyFile(fromRel: string, toRel: string): void {
  const from = resolve(ROOT, fromRel);
  const to = resolve(ROOT, toRel);
  mkdirSync(resolve(to, '..'), { recursive: true });
  cpSync(from, to);
}

function buildIOSFonts(families: FontFamily[], tokens: FontToken[]): void {
  const swiftOut = `${IOS_FONTS_OUT}/NucleusFont+Defaults.swift`;
  writeOut(swiftOut, generateIOSFonts(tokens));

  const copied: string[] = [];
  for (const family of families) {
    const dest = `${IOS_FONTS_RESOURCES_OUT}/${family.file}`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${family.file}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (ios)', [
    ['ios', swiftOut],
    ...copied.map((path) => ['ios', path] as const),
  ]);
}

function buildAndroidFonts(families: FontFamily[], tokens: FontToken[]): void {
  const kotlinOut = `${ANDROID_TOKENS_OUT}/NucleusFonts.kt`;
  writeOut(kotlinOut, generateAndroidFonts(tokens));

  const copied: string[] = [];
  for (const family of families) {
    const resource = snakeCase(family.file);
    const dest = `${ANDROID_RES_OUT}/font/${resource}.ttf`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${family.file}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (android)', [
    ['android', kotlinOut],
    ...copied.map((path) => ['android', path] as const),
  ]);
}

function buildWebFonts(families: FontFamily[], tokens: FontToken[]): void {
  const { variablesCss, json } = generateWebFonts(tokens);

  const cssOut = `${WEB_OUT}/nucleus-fonts.css`;
  const jsonOut = `${WEB_OUT}/nucleus-fonts.json`;
  writeOut(cssOut, variablesCss);
  writeOut(jsonOut, json);

  const copied: string[] = [];
  for (const family of families) {
    const dest = `${WEB_FONTS_OUT}/${family.file}`;
    copyFile(`${FONT_DEFINITIONS_DIR}/${family.file}`, dest);
    copied.push(dest);
  }

  logStage('tokens/definitions/font (web)', [
    ['web', cssOut],
    ['web', jsonOut],
    ...copied.map((path) => ['web', path] as const),
  ]);
}

export function buildFonts(): void {
  const { families, tokens } = loadFontDefinitions(FONT_SOURCE);
  buildIOSFonts(families, tokens);
  buildAndroidFonts(families, tokens);
  buildWebFonts(families, tokens);
}
