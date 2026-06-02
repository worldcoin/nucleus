import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import {
  generateWebButtonJson,
  loadButtonDefinition,
  resolveButtonStyles,
} from '../formats/buttons.js';
import { ROOT, WEB_OUT, logStage } from './shared.js';

export const BUTTON_SOURCE = 'tokens/definitions/component/button.json';

export function buildButtons(): void {
  const styles = resolveButtonStyles(loadButtonDefinition(BUTTON_SOURCE));

  const out = `${WEB_OUT}/nucleus-button.json`;
  const absolute = resolve(ROOT, out);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, generateWebButtonJson(styles));

  logStage('tokens/definitions/component/button', [['web', out]]);
}
