import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

export const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');
export const BUILD_VERSION = readFileSync(resolve(ROOT, 'VERSION'), 'utf8').trim();

export const ANDROID_TOKENS_OUT = 'android/nucleus/src/main/java/com/worldcoin/nucleus/tokens';
export const ANDROID_RES_OUT = 'android/nucleus/src/main/res';
export const IOS_COLORS_OUT = 'ios/Sources/NucleusColors';
export const IOS_FONTS_OUT = 'ios/Sources/NucleusFonts';
export const IOS_FONTS_RESOURCES_OUT = `${IOS_FONTS_OUT}/Resources/Fonts`;
export const WEB_OUT = 'build/web';
export const WEB_FONTS_OUT = `${WEB_OUT}/fonts`;

export function logStage(header: string, files: ReadonlyArray<readonly [string, string]>): void {
  console.log(`\n${header}`);
  for (const [platform, output] of files) {
    console.log(`  ${platform.padEnd(8)}✔︎ ${output}`);
  }
}
