import { camelCasePath, publicColorPath } from './shared.js';
import type { ResolvedButtonStyle } from './buttons.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';

function colorAccessor(path: string): string {
  return camelCasePath(publicColorPath(path.split('.')));
}

function valName(style: ResolvedButtonStyle): string {
  return `${style.variant}${style.size}`;
}

function fontAccessor(path: string): string {
  return path.split('.').at(-1) ?? path;
}

/**
 * Theme-aware color pair referencing the existing semantic-color objects, wrapped across
 * lines so generated Kotlin stays within ktlint's 120-char limit (android/.editorconfig).
 */
function colorField(field: string, path: string): string[] {
  const accessor = colorAccessor(path);
  return [
    `        ${field} = NucleusButtonColor(`,
    `            NucleusSemanticColorsLight.${accessor},`,
    `            NucleusSemanticColorsDark.${accessor},`,
    `        ),`,
  ];
}

export function generateAndroidButtons(styles: ResolvedButtonStyle[]): string {
  const lines: string[] = [
    '// This file is auto-generated. Do not edit manually.',
    '',
    `package ${PACKAGE_NAME}`,
    '',
    'import androidx.compose.ui.graphics.Color',
    'import androidx.compose.ui.unit.Dp',
    'import androidx.compose.ui.unit.dp',
    '',
    '/** A theme-aware color pair for a button token. */',
    'data class NucleusButtonColor(val light: Color, val dark: Color) {',
    '    fun color(isDark: Boolean): Color = if (isDark) dark else light',
    '}',
    '',
    '/** A Nucleus button style token: colors, geometry, and label font for a variant × size. */',
    'data class NucleusButtonStyle(',
    '    val background: NucleusButtonColor,',
    '    val content: NucleusButtonColor,',
    '    val border: NucleusButtonColor?,',
    '    val height: Dp,',
    '    val cornerRadius: Dp,',
    '    val paddingHorizontal: Dp,',
    '    val paddingVertical: Dp,',
    '    val font: NucleusFontStyle,',
    '    val pressedInset: Dp,',
    ')',
    '',
    'object NucleusButtons {',
  ];

  for (const style of styles) {
    lines.push(`    val ${valName(style)} = NucleusButtonStyle(`);
    lines.push(...colorField('background', style.background));
    lines.push(...colorField('content', style.content));
    if (style.border) {
      lines.push(...colorField('border', style.border));
    } else {
      lines.push('        border = null,');
    }
    lines.push(
      `        height = ${style.height}.dp,`,
      `        cornerRadius = ${style.cornerRadius}.dp,`,
      `        paddingHorizontal = ${style.paddingHorizontal}.dp,`,
      `        paddingVertical = ${style.paddingVertical}.dp,`,
      `        font = NucleusFonts.${fontAccessor(style.font)},`,
      `        pressedInset = ${style.pressedInset}.dp,`,
      `    )`,
    );
  }

  lines.push('}', '');
  return lines.join('\n');
}
