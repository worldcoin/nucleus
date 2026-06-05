import { camelCasePath, publicColorPath } from './shared.js';
import type { ResolvedButtonStyle } from './buttons.js';

/** Color token path → NucleusColor accessor, e.g. `semantic.color.action.primary` → `actionPrimary`. */
function colorAccessor(path: string): string {
  return camelCasePath(publicColorPath(path.split('.')));
}

/** Combined token → Swift static name, e.g. `component.button.primary.48` → `primary48`. */
function caseName(style: ResolvedButtonStyle): string {
  return `${style.variant}${style.size}`;
}

/** Typography path → NucleusFont accessor (bare id), e.g. `typography.subtitle.s1` → `s1`. */
function fontAccessor(path: string): string {
  return path.split('.').at(-1) ?? path;
}

export function generateIOSButtons(styles: ResolvedButtonStyle[]): string {
  const lines: string[] = [
    '// This file is auto-generated. Do not edit manually.',
    '',
    'import NucleusColors',
    'import NucleusFonts',
    '',
    'public extension NucleusButton {',
  ];

  for (const style of styles) {
    const border = style.border ? `.${colorAccessor(style.border)}` : 'nil';
    lines.push(
      `    static let ${caseName(style)} = NucleusButton(`,
      `        background: .${colorAccessor(style.background)},`,
      `        content: .${colorAccessor(style.content)},`,
      `        border: ${border},`,
      `        height: ${style.height},`,
      `        cornerRadius: ${style.cornerRadius},`,
      `        paddingHorizontal: ${style.paddingHorizontal},`,
      `        paddingVertical: ${style.paddingVertical},`,
      `        font: .${fontAccessor(style.font)},`,
      `        pressedInset: ${style.pressedInset}`,
      `    )`,
    );
  }

  lines.push('}', '');
  return lines.join('\n');
}
