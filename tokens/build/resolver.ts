import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { generateAndroidResolver } from '../formats/resolver-android.js';
import { generateIOSResolver } from '../formats/resolver-ios.js';
import { buildTokenCatalog } from '../formats/resolver-shared.js';
import {
  generateWebTokenPathsDts,
  generateWebTokenPathsJs,
} from '../formats/resolver-web.js';
import {
  ANDROID_TOKENS_OUT,
  IOS_RESOLVER_OUT,
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
 * Generates the cross-platform token-path ↔ token resolver from the same definitions that drive
 * the static accessors: native `NucleusTokenResolver` (string → token) for iOS + Android, and the
 * web `token-paths` constants module (canonical path strings) that the app-backend SDUI library
 * imports instead of hand-maintaining its own path literals.
 */
export function buildResolver(): void {
  const catalog = buildTokenCatalog();

  const iosOut = `${IOS_RESOLVER_OUT}/NucleusTokenResolver.swift`;
  writeOut(iosOut, generateIOSResolver(catalog));

  const androidOut = `${ANDROID_TOKENS_OUT}/NucleusTokenResolver.kt`;
  writeOut(androidOut, generateAndroidResolver(catalog));

  const webJsOut = `${WEB_OUT}/token-paths.js`;
  writeOut(webJsOut, generateWebTokenPathsJs(catalog));

  const webDtsOut = `${WEB_OUT}/token-paths.d.ts`;
  writeOut(webDtsOut, generateWebTokenPathsDts(catalog));

  logStage('token resolver (path ↔ token)', [
    ['ios', iosOut],
    ['android', androidOut],
    ['web', webJsOut],
    ['web', webDtsOut],
  ]);
}
