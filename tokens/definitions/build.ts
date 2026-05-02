import StyleDictionary from 'style-dictionary';
import { cpSync, mkdirSync, existsSync, readFileSync, writeFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';
import type { Format, NameTransform, TransformedToken } from 'style-dictionary/types';

import { composeColorObject } from '../formats/android.js';
import { generateIOSColors } from '../formats/ios.js';
import { kebabCasePath } from '../formats/shared.js';
import { cssColorVariables, jsonFlat } from '../formats/web.js';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');
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
const primitiveSource = 'tokens/definitions/color/primitive.json';
const semanticLightSource = 'tokens/definitions/color/semantic.light.json';
const semanticDarkSource = 'tokens/definitions/color/semantic.dark.json';
const androidOut = 'android/nucleus/src/main/java/com/worldcoin/nucleus/tokens';
const iosOut = 'ios/Sources/NucleusColors';
const webOut = 'build/web';
const BUILD_VERSION = readFileSync(resolve(ROOT, 'VERSION'), 'utf8').trim();

type TokenRoot = 'primitive' | 'semantic';

interface TokenBuildTarget {
  source: string[];
  include?: string[];
  tokenRoot: TokenRoot;
  androidDestination: string;
  androidObjectName: string;
  webBaseName: string;
}

const buildTargets: TokenBuildTarget[] = [
  {
    source: [primitiveSource],
    tokenRoot: 'primitive',
    androidDestination: 'NucleusPrimitiveColors.kt',
    androidObjectName: 'NucleusPrimitiveColors',
    webBaseName: 'nucleus-primitive-colors',
  },
  {
    source: [semanticLightSource],
    include: [primitiveSource],
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsLight.kt',
    androidObjectName: 'NucleusSemanticColorsLight',
    webBaseName: 'nucleus-semantic-colors-light',
  },
  {
    source: [semanticDarkSource],
    include: [primitiveSource],
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsDark.kt',
    androidObjectName: 'NucleusSemanticColorsDark',
    webBaseName: 'nucleus-semantic-colors-dark',
  },
];

function isTokenInRoot(token: TransformedToken, root: TokenRoot): boolean {
  return token.$type === 'color' && token.path[0] === root;
}

function logBuiltAndroidWeb(target: TokenBuildTarget): void {
  const header = target.source[0];
  const files = [
    ['android', `${androidOut}/${target.androidDestination}`],
    ['web', `${webOut}/${target.webBaseName}.css`],
    ['web', `${webOut}/${target.webBaseName}.json`],
  ] as const;

  console.log(`\n${header}`);
  for (const [platform, output] of files) {
    console.log(`  ${platform.padEnd(8)}✔︎ ${output}`);
  }
}

async function buildAndroidAndWebTokens(): Promise<void> {
  for (const target of buildTargets) {
    const sd = new StyleDictionary({
      log: { verbosity: 'silent' },
      include: target.include,
      source: target.source,
      platforms: {
        android: {
          buildPath: `${androidOut}/`,
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
          buildPath: `${webOut}/`,
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
    logBuiltAndroidWeb(target);
  }
}

// the iOS build sits outside the per-source loop because the generated
// NucleusColor+Semantics.swift merges light + dark into a single file.
function buildIOSTokens(): void {
  const { primitivesContent, semanticsContent } = generateIOSColors({
    primitiveSource,
    semanticLightSource,
    semanticDarkSource,
  });

  const targetDir = resolve(ROOT, iosOut);
  mkdirSync(targetDir, { recursive: true });

  writeFileSync(resolve(targetDir, 'NucleusColor+Primitives.swift'), primitivesContent);
  writeFileSync(resolve(targetDir, 'NucleusColor+Semantics.swift'), semanticsContent);

  console.log('\ntokens/definitions/color (ios)');
  console.log(`  ios     ✔︎ ${iosOut}/NucleusColor+Primitives.swift`);
  console.log(`  ios     ✔︎ ${iosOut}/NucleusColor+Semantics.swift`);
}

interface TemplateCopy {
  from: string;
  to: string;
}

function copyTemplates(): void {
  const copies: TemplateCopy[] = [
    {
      from: 'tokens/templates/web/package.json',
      to: 'build/web/package.json',
    },
    {
      from: 'tokens/templates/web/index.d.ts',
      to: 'build/web/index.d.ts',
    },
  ];

  for (const { from, to } of copies) {
    const src = resolve(ROOT, from);
    const dest = resolve(ROOT, to);
    if (!existsSync(src)) {
      console.warn(`  ⚠ template not found: ${from}`);
      continue;
    }
    mkdirSync(dirname(dest), { recursive: true });
    cpSync(src, dest);
  }

  const webPackagePath = resolve(ROOT, 'build/web/package.json');
  if (existsSync(webPackagePath)) {
    const webPackage = JSON.parse(readFileSync(webPackagePath, 'utf8')) as Record<string, unknown>;
    webPackage.version = BUILD_VERSION;
    writeFileSync(webPackagePath, `${JSON.stringify(webPackage, null, 2)}\n`);
  }

  console.log('✓ Templates copied');
}

async function main(): Promise<void> {
  console.log('Building Nucleus tokens…');
  await buildAndroidAndWebTokens();
  buildIOSTokens();
  console.log('\n✓ Tokens built');
  copyTemplates();
  console.log('\nDone!');
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
