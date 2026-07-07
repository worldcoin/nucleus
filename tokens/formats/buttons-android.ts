import Handlebars from 'handlebars';

import { camelCasePath, publicColorPath, readTemplate } from './shared.js';
import type { ResolvedButtonStyle } from './buttons.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusButtons.kt.hbs'),
);

interface AndroidButtonEntry {
  name: string;
  background: string;
  content: string;
  border: string | null;
  height: number;
  cornerRadius: number;
  paddingHorizontal: number;
  paddingVertical: number;
  font: string;
  pressedInset: number;
}

function colorAccessor(path: string): string {
  return camelCasePath(publicColorPath(path.split('.')));
}

function toEntry(style: ResolvedButtonStyle): AndroidButtonEntry {
  return {
    name: `${style.variant}${style.size}`,
    background: colorAccessor(style.background),
    content: colorAccessor(style.content),
    border: style.border ? colorAccessor(style.border) : null,
    height: style.height,
    cornerRadius: style.cornerRadius,
    paddingHorizontal: style.paddingHorizontal,
    paddingVertical: style.paddingVertical,
    font: style.font,
    pressedInset: style.pressedInset,
  };
}

export function generateAndroidButtons(styles: ResolvedButtonStyle[]): string {
  return TEMPLATE({ packageName: PACKAGE_NAME, styles: styles.map(toEntry) });
}
