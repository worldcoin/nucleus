import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { generateAndroidButtons } from '../formats/buttons-android.js';
import { generateIOSButtons } from '../formats/buttons-ios.js';
import {
  generateWebButtonJson,
  loadButtonDefinition,
  resolveButtonStyles,
} from '../formats/buttons.js';
import {
  ANDROID_TOKENS_OUT,
  IOS_BUTTONS_OUT,
  ROOT,
  WEB_OUT,
  logStage,
} from './shared.js';

export const BUTTON_SOURCE = 'tokens/definitions/component/button.json';

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

export function buildButtons(): void {
  const styles = resolveButtonStyles(loadButtonDefinition(BUTTON_SOURCE));

  const iosOut = `${IOS_BUTTONS_OUT}/NucleusButton+Defaults.swift`;
  writeOut(iosOut, generateIOSButtons(styles));

  const androidOut = `${ANDROID_TOKENS_OUT}/NucleusButtons.kt`;
  writeOut(androidOut, generateAndroidButtons(styles));

  const webOut = `${WEB_OUT}/nucleus-button.json`;
  writeOut(webOut, generateWebButtonJson(styles));

  logStage('tokens/definitions/component/button', [
    ['ios', iosOut],
    ['android', androidOut],
    ['web', webOut],
  ]);
}
