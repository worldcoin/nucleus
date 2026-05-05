import StyleDictionary from 'style-dictionary';
import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';
import type { Format, NameTransform, TransformedToken } from 'style-dictionary/types';

import { composeColorObject } from '../formats/colors-android.js';
import { generateIOSColors } from '../formats/colors-ios.js';
import { kebabCasePath } from '../formats/shared.js';
import { cssColorVariables, jsonFlat } from '../formats/colors-web.js';
import { ANDROID_TOKENS_OUT, IOS_COLORS_OUT, ROOT, WEB_OUT, logStage } from './shared.js';

const PRIMITIVE_SOURCE = 'tokens/definitions/color/primitive.json';
const SEMANTIC_LIGHT_SOURCE = 'tokens/definitions/color/semantic.light.json';
const SEMANTIC_DARK_SOURCE = 'tokens/definitions/color/semantic.dark.json';

const nameTransform: NameTransform = {
  name: 'name/nucleus',
  type: 'name',
  transform: (token) => kebabCasePath(token.path),
};

const allFormats: Format[] = [
  composeColorObject,
  cssColorVariables,
  jsonFlat,
];

type TokenRoot = 'primitive' | 'semantic';

interface AndroidWebTarget {
  source: string[];
  include?: string[];
  tokenRoot: TokenRoot;
  androidDestination: string;
  androidObjectName: string;
  webBaseName: string;
}

const androidWebTargets: AndroidWebTarget[] = [
  {
    source: [PRIMITIVE_SOURCE],
    tokenRoot: 'primitive',
    androidDestination: 'NucleusPrimitiveColors.kt',
    androidObjectName: 'NucleusPrimitiveColors',
    webBaseName: 'nucleus-primitive-colors',
  },
  {
    source: [SEMANTIC_LIGHT_SOURCE],
    include: [PRIMITIVE_SOURCE],
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsLight.kt',
    androidObjectName: 'NucleusSemanticColorsLight',
    webBaseName: 'nucleus-semantic-colors-light',
  },
  {
    source: [SEMANTIC_DARK_SOURCE],
    include: [PRIMITIVE_SOURCE],
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsDark.kt',
    androidObjectName: 'NucleusSemanticColorsDark',
    webBaseName: 'nucleus-semantic-colors-dark',
  },
];

function isTokenInRoot(token: TransformedToken, root: TokenRoot): boolean {
  return token.$type === 'color' && token.path[0] === root;
}

async function buildAndroidAndWebColors(): Promise<void> {
  for (const target of androidWebTargets) {
    const sd = new StyleDictionary({
      log: { verbosity: 'silent' },
      include: target.include,
      source: target.source,
      platforms: {
        android: {
          buildPath: `${ANDROID_TOKENS_OUT}/`,
          transforms: [nameTransform.name],
          files: [
            {
              destination: target.androidDestination,
              format: 'compose/colorObject',
              options: { objectName: target.androidObjectName },
              filter: (token: TransformedToken) => isTokenInRoot(token, target.tokenRoot),
            },
          ],
        },
        web: {
          buildPath: `${WEB_OUT}/`,
          transforms: [nameTransform.name],
          files: [
            {
              destination: `${target.webBaseName}.css`,
              format: 'css/colorVariables',
              filter: (token: TransformedToken) => isTokenInRoot(token, target.tokenRoot),
            },
            {
              destination: `${target.webBaseName}.json`,
              format: 'json/flat',
              filter: (token: TransformedToken) => isTokenInRoot(token, target.tokenRoot),
            },
          ],
        },
      },
    });

    for (const fmt of allFormats) {
      sd.registerFormat(fmt);
    }
    sd.registerTransform(nameTransform);

    await sd.buildAllPlatforms();

    logStage(target.source[0], [
      ['android', `${ANDROID_TOKENS_OUT}/${target.androidDestination}`],
      ['web', `${WEB_OUT}/${target.webBaseName}.css`],
      ['web', `${WEB_OUT}/${target.webBaseName}.json`],
    ]);
  }
}

// the iOS build sits outside the per-source loop because the generated
// NucleusColor+Semantics.swift merges light + dark into a single file.
function buildIOSColors(): void {
  const { primitivesContent, semanticsContent } = generateIOSColors({
    primitiveSource: PRIMITIVE_SOURCE,
    semanticLightSource: SEMANTIC_LIGHT_SOURCE,
    semanticDarkSource: SEMANTIC_DARK_SOURCE,
  });

  const targetDir = resolve(ROOT, IOS_COLORS_OUT);
  mkdirSync(targetDir, { recursive: true });

  writeFileSync(resolve(targetDir, 'NucleusColor+Primitives.swift'), primitivesContent);
  writeFileSync(resolve(targetDir, 'NucleusColor+Semantics.swift'), semanticsContent);

  logStage('tokens/definitions/color (ios)', [
    ['ios', `${IOS_COLORS_OUT}/NucleusColor+Primitives.swift`],
    ['ios', `${IOS_COLORS_OUT}/NucleusColor+Semantics.swift`],
  ]);
}

export async function buildColors(): Promise<void> {
  await buildAndroidAndWebColors();
  buildIOSColors();
}
