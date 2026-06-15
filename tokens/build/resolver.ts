import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { generateAndroidResolver } from '../formats/resolver-android.js';
import {
  generateIOSButtonResolvable,
  generateIOSColorResolvable,
  generateIOSFontResolvable,
  generateIOSIconResolvable,
} from '../formats/resolver-ios.js';
import { buildTokenCatalog } from '../formats/resolver-shared.js';
import {
  generateWebTokenPathsDts,
  generateWebTokenPathsJs,
} from '../formats/resolver-web.js';
import {
  ANDROID_TOKENS_OUT,
  IOS_BUTTONS_OUT,
  IOS_COLORS_OUT,
  IOS_FONTS_OUT,
  IOS_ICONS_OUT,
  ROOT,
  WEB_OUT,
  logStage,
} from './shared.js';

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

/**
 * Generates the cross-platform token resolver from the same definitions that drive the static
 * accessors: per-type iOS `TokenResolvable` conformances (each in its own target — no module that
 * imports everything), the single-module Android `NucleusTokenResolver`, and the web `token-paths`
 * constants the app-backend SDUI library imports instead of hand-maintaining token literals.
 */
export function buildResolver(): void {
  const catalog = buildTokenCatalog();

  const iosColor = `${IOS_COLORS_OUT}/NucleusColor+TokenResolvable.swift`;
  writeOut(iosColor, generateIOSColorResolvable(catalog));

  const iosFont = `${IOS_FONTS_OUT}/NucleusFont+TokenResolvable.swift`;
  writeOut(iosFont, generateIOSFontResolvable(catalog));

  const iosButton = `${IOS_BUTTONS_OUT}/NucleusButton+TokenResolvable.swift`;
  writeOut(iosButton, generateIOSButtonResolvable(catalog));

  const iosIcon = `${IOS_ICONS_OUT}/NucleusIcon+TokenResolvable.swift`;
  writeOut(iosIcon, generateIOSIconResolvable(catalog));

  const androidOut = `${ANDROID_TOKENS_OUT}/NucleusTokenResolver.kt`;
  writeOut(androidOut, generateAndroidResolver(catalog));

  const webJsOut = `${WEB_OUT}/token-paths.js`;
  writeOut(webJsOut, generateWebTokenPathsJs(catalog));

  const webDtsOut = `${WEB_OUT}/token-paths.d.ts`;
  writeOut(webDtsOut, generateWebTokenPathsDts(catalog));

  logStage('token resolver (type-scoped tokens → tokens)', [
    ['ios', iosColor],
    ['ios', iosFont],
    ['ios', iosButton],
    ['ios', iosIcon],
    ['android', androidOut],
    ['web', webJsOut],
    ['web', webDtsOut],
  ]);
}
