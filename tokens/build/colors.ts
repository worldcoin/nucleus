import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import {
  generateAndroidPrimitiveColors,
  generateAndroidSemanticColors,
} from '../formats/colors-android.js';
import { generateIOSColors } from '../formats/colors-ios.js';
import { generateWebColorCss, generateWebColorJson } from '../formats/colors-web.js';
import { loadColorTokens } from '../formats/loaders.js';
import { ANDROID_TOKENS_OUT, IOS_COLORS_OUT, ROOT, WEB_OUT, logStage } from './shared.js';

const PRIMITIVE_SOURCE = 'tokens/definitions/color/primitive.json';
const SEMANTIC_LIGHT_SOURCE = 'tokens/definitions/color/semantic.light.json';
const SEMANTIC_DARK_SOURCE = 'tokens/definitions/color/semantic.dark.json';

interface ColorBuildTarget {
  source: string;
  androidFileName: string;
  androidObjectName: string;
  webBaseName: string;
  /** When true, treat as primitive (no reference resolution). */
  primitive?: boolean;
}

const targets: ColorBuildTarget[] = [
  {
    source: PRIMITIVE_SOURCE,
    androidFileName: 'NucleusPrimitiveColors.kt',
    androidObjectName: 'NucleusPrimitiveColors',
    webBaseName: 'nucleus-primitive-colors',
    primitive: true,
  },
  {
    source: SEMANTIC_LIGHT_SOURCE,
    androidFileName: 'NucleusSemanticColorsLight.kt',
    androidObjectName: 'NucleusSemanticColorsLight',
    webBaseName: 'nucleus-semantic-colors-light',
  },
  {
    source: SEMANTIC_DARK_SOURCE,
    androidFileName: 'NucleusSemanticColorsDark.kt',
    androidObjectName: 'NucleusSemanticColorsDark',
    webBaseName: 'nucleus-semantic-colors-dark',
  },
];

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

function buildAndroidAndWebColors(): void {
  for (const target of targets) {
    const tokens = loadColorTokens(target.source);

    const androidContent = target.primitive
      ? generateAndroidPrimitiveColors(tokens)
      : generateAndroidSemanticColors(target.androidObjectName, tokens);
    const androidOut = `${ANDROID_TOKENS_OUT}/${target.androidFileName}`;
    writeOut(androidOut, androidContent);

    const cssOut = `${WEB_OUT}/${target.webBaseName}.css`;
    const jsonOut = `${WEB_OUT}/${target.webBaseName}.json`;
    writeOut(cssOut, generateWebColorCss(tokens));
    writeOut(jsonOut, generateWebColorJson(tokens));

    logStage(target.source, [
      ['android', androidOut],
      ['web', cssOut],
      ['web', jsonOut],
    ]);
  }
}

// The iOS build sits outside the per-source loop because the generated
// NucleusColor+Semantics.swift merges light + dark into a single file.
function buildIOSColors(): void {
  const { primitivesContent, semanticsContent } = generateIOSColors({
    primitiveSource: PRIMITIVE_SOURCE,
    semanticLightSource: SEMANTIC_LIGHT_SOURCE,
    semanticDarkSource: SEMANTIC_DARK_SOURCE,
  });

  writeOut(`${IOS_COLORS_OUT}/NucleusColor+Primitives.swift`, primitivesContent);
  writeOut(`${IOS_COLORS_OUT}/NucleusColor+Semantics.swift`, semanticsContent);

  logStage('tokens/definitions/color (ios)', [
    ['ios', `${IOS_COLORS_OUT}/NucleusColor+Primitives.swift`],
    ['ios', `${IOS_COLORS_OUT}/NucleusColor+Semantics.swift`],
  ]);
}

export function buildColors(): void {
  buildAndroidAndWebColors();
  buildIOSColors();
}
