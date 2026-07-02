import Handlebars from 'handlebars';

import { camelCasePath, publicColorPath, readTemplate } from './shared.js';
import type { ResolvedButtonStyle } from './buttons.js';

const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusButton+Defaults.swift.hbs'),
);

interface IOSButtonEntry {
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

/** Color token path → NucleusColor accessor, e.g. `semantic.color.action.primary` → `actionPrimary`. */
function colorAccessor(path: string): string {
  return camelCasePath(publicColorPath(path.split('.')));
}

function toEntry(style: ResolvedButtonStyle): IOSButtonEntry {
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

export function generateIOSButtons(styles: ResolvedButtonStyle[]): string {
  return TEMPLATE({ styles: styles.map(toEntry) });
}
