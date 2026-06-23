import { cpSync, mkdirSync, readdirSync, readFileSync, rmSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import {
  ICON_DEFINITIONS_DIR,
  discoverIconTokens,
  iconAssetBaseName,
  type IconToken,
  type IconVariant,
} from '../formats/icons-shared.js';
import {
  androidDrawableName,
  generateAndroidIcons,
  svgToVectorDrawable,
} from '../formats/icons-android.js';
import {
  catalogContentsJson,
  generateIOSIcons,
  imagesetContentsJson,
  planIOSImagesets,
} from '../formats/icons-ios.js';
import { generateWebIconsManifest, planWebIcons } from '../formats/icons-web.js';
import {
  ANDROID_RES_OUT,
  ANDROID_TOKENS_OUT,
  IOS_ICONS_ASSETS_OUT,
  IOS_ICONS_OUT,
  ROOT,
  WEB_ICONS_OUT,
  WEB_OUT,
  logStage,
} from './shared.js';

function buildIOSIcons(tokens: IconToken[]): void {
  // wipe and rebuild the xcasset so removed icons don't linger
  const assetsRoot = resolve(ROOT, IOS_ICONS_ASSETS_OUT);
  rmSync(assetsRoot, { recursive: true, force: true });
  mkdirSync(assetsRoot, { recursive: true });
  writeFileSync(resolve(assetsRoot, 'Contents.json'), catalogContentsJson());

  const plans = planIOSImagesets(tokens);
  for (const plan of plans) {
    const imagesetDir = resolve(assetsRoot, `${plan.name}.imageset`);
    mkdirSync(imagesetDir, { recursive: true });
    const filename = `${plan.name}.svg`;
    cpSync(
      resolve(ROOT, ICON_DEFINITIONS_DIR, plan.variant, `${plan.iconName}.svg`),
      resolve(imagesetDir, filename),
    );
    writeFileSync(resolve(imagesetDir, 'Contents.json'), imagesetContentsJson(filename));
  }

  const swiftDir = resolve(ROOT, IOS_ICONS_OUT);
  mkdirSync(swiftDir, { recursive: true });
  writeFileSync(resolve(swiftDir, 'NucleusIcon+Symbol.swift'), generateIOSIcons(tokens));

  logStage('tokens/definitions/icons (ios)', [
    ['ios', `${IOS_ICONS_OUT}/NucleusIcon+Symbol.swift`],
    ['ios', `${IOS_ICONS_ASSETS_OUT} (${plans.length} imagesets)`],
  ]);
}

function buildAndroidIcons(tokens: IconToken[]): void {
  const drawableDir = resolve(ROOT, ANDROID_RES_OUT, 'drawable');
  mkdirSync(drawableDir, { recursive: true });

  // wipe previously-generated nucleus icons so removed icons don't linger,
  // but leave any other drawables in this folder untouched
  for (const file of readdirSync(drawableDir)) {
    if (file.startsWith('nucleus_icon_')) {
      rmSync(resolve(drawableDir, file));
    }
  }

  let drawableCount = 0;
  for (const token of tokens) {
    for (const variant of token.variants) {
      const svg = readFileSync(
        resolve(ROOT, ICON_DEFINITIONS_DIR, variant, `${token.name}.svg`),
        'utf8',
      );
      const xml = svgToVectorDrawable(svg);
      const filename = `${androidDrawableName(token, variant)}.xml`;
      writeFileSync(resolve(drawableDir, filename), xml);
      drawableCount += 1;
    }
  }

  const tokensDir = resolve(ROOT, ANDROID_TOKENS_OUT);
  mkdirSync(tokensDir, { recursive: true });
  writeFileSync(resolve(tokensDir, 'NucleusIcon.kt'), generateAndroidIcons(tokens));

  logStage('tokens/definitions/icons (android)', [
    ['android', `${ANDROID_TOKENS_OUT}/NucleusIcon.kt`],
    ['android', `${ANDROID_RES_OUT}/drawable (${drawableCount} vector drawables)`],
  ]);
}

function buildWebIcons(tokens: IconToken[]): void {
  const outDir = resolve(ROOT, WEB_ICONS_OUT);
  rmSync(outDir, { recursive: true, force: true });
  mkdirSync(outDir, { recursive: true });

  const plans = planWebIcons(tokens);
  for (const plan of plans) {
    for (const variant of Object.keys(plan.files) as IconVariant[]) {
      const filename = `${iconAssetBaseName(plan.name, variant)}.svg`;
      cpSync(
        resolve(ROOT, ICON_DEFINITIONS_DIR, variant, `${plan.name}.svg`),
        resolve(outDir, filename),
      );
    }
  }

  writeFileSync(resolve(ROOT, WEB_OUT, 'nucleus-icons.json'), generateWebIconsManifest(tokens));

  logStage('tokens/definitions/icons (web)', [
    ['web', `${WEB_OUT}/nucleus-icons.json`],
    ['web', `${WEB_ICONS_OUT} (${plans.length} svgs)`],
  ]);
}

export function buildIcons(): void {
  const tokens = discoverIconTokens();
  buildIOSIcons(tokens);
  buildAndroidIcons(tokens);
  buildWebIcons(tokens);
}
