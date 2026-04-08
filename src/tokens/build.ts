import StyleDictionary from 'style-dictionary';
import { cpSync, mkdirSync, existsSync, readFileSync, writeFileSync } from 'fs';
import { resolve, dirname, basename } from 'path';
import { fileURLToPath } from 'url';
import type { Format, NameTransform, TransformedToken } from 'style-dictionary/types';

import { composeColorObject } from '../formats/android.js';
import { swiftColorDefaults } from '../formats/ios.js';
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
  swiftColorDefaults,
  cssColorVariables,
  jsonFlat,
];
const primitiveSources: string[] = ['src/tokens/color/primitive.json'];
const semanticLightSources: string[] = ['src/tokens/color/semantic.light.json'];
const semanticDarkSources: string[] = ['src/tokens/color/semantic.dark.json'];
const androidOut = 'build/android/src/main/kotlin/com/worldcoin/nucleus';
const iosOut = 'build/ios/Sources/Nucleus';
const webOut = 'build/web';
const BUILD_VERSION = readFileSync(resolve(ROOT, 'VERSION'), 'utf8').trim();

type TokenRoot = 'primitive' | 'semantic';

interface TokenBuildTarget {
  source: string[];
  include?: string[];
  tokenRoot: TokenRoot;
  androidDestination: string;
  androidObjectName: string;
  iosDestination: string;
  iosEnumName: string;
  webBaseName: string;
}

const buildTargets: TokenBuildTarget[] = [
  {
    source: primitiveSources,
    tokenRoot: 'primitive',
    androidDestination: 'NucleusPrimitiveColors.kt',
    androidObjectName: 'NucleusPrimitiveColors',
    iosDestination: 'NucleusPrimitiveColors.swift',
    iosEnumName: 'NucleusPrimitiveColors',
    webBaseName: 'nucleus-primitive-colors',
  },
  {
    source: semanticLightSources,
    include: primitiveSources,
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsLight.kt',
    androidObjectName: 'NucleusSemanticColorsLight',
    iosDestination: 'NucleusSemanticColorsLight.swift',
    iosEnumName: 'NucleusSemanticColorsLight',
    webBaseName: 'nucleus-semantic-colors-light',
  },
  {
    source: semanticDarkSources,
    include: primitiveSources,
    tokenRoot: 'semantic',
    androidDestination: 'NucleusSemanticColorsDark.kt',
    androidObjectName: 'NucleusSemanticColorsDark',
    iosDestination: 'NucleusSemanticColorsDark.swift',
    iosEnumName: 'NucleusSemanticColorsDark',
    webBaseName: 'nucleus-semantic-colors-dark',
  },
];

function isTokenInRoot(token: TransformedToken, root: TokenRoot): boolean {
  return token.$type === 'color' && token.path[0] === root;
}

function logBuiltFiles(target: TokenBuildTarget): void {
  const header = target.source[0];
  const files = [
    ['android', `${androidOut}/${target.androidDestination}`],
    ['ios', `${iosOut}/${target.iosDestination}`],
    ['web', `${webOut}/${target.webBaseName}.css`],
    ['web', `${webOut}/${target.webBaseName}.json`],
  ] as const;

  console.log(`\n${header}`);
  for (const [platform, output] of files) {
    console.log(`  ${platform.padEnd(8)}✔︎ ${output}`);
  }
}

async function buildTokens(): Promise<void> {
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
        ios: {
          buildPath: `${iosOut}/`,
          transforms: [nameTransform.name],
          files: [
            {
              destination: target.iosDestination,
              format: 'swift/nucleusColorDefaults',
              options: { enumName: target.iosEnumName },
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
    logBuiltFiles(target);
  }

  console.log('\n\u2713 Tokens built');
}

interface TemplateCopy {
  from: string;
  to: string;
}

function copyTemplates(): void {
  const copies: TemplateCopy[] = [
    {
      from: 'src/templates/android/build.gradle.kts',
      to: 'build/android/build.gradle.kts',
    },
    {
      from: 'src/templates/android/settings.gradle.kts',
      to: 'build/android/settings.gradle.kts',
    },
    {
      from: 'src/templates/ios/Package.swift',
      to: 'build/ios/Package.swift',
    },
    {
      from: 'src/templates/web/package.json',
      to: 'build/web/package.json',
    },
    {
      from: 'src/templates/web/index.d.ts',
      to: 'build/web/index.d.ts',
    },
  ];

  for (const { from, to } of copies) {
    const src = resolve(ROOT, from);
    const dest = resolve(ROOT, to);
    if (!existsSync(src)) {
      console.warn(`  \u26A0 template not found: ${from}`);
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

  const androidBuildScriptPath = resolve(ROOT, 'build/android/build.gradle.kts');
  if (existsSync(androidBuildScriptPath)) {
    const androidBuildScript = readFileSync(androidBuildScriptPath, 'utf8').replace(
      '__VERSION__',
      BUILD_VERSION,
    );
    writeFileSync(androidBuildScriptPath, androidBuildScript);
  }

  console.log('\u2713 Templates copied');
}

async function main(): Promise<void> {
  console.log('Building Nucleus tokens\u2026');
  await buildTokens();
  copyTemplates();
  console.log('\nDone!');
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
