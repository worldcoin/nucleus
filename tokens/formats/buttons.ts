import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

import { parseReference, typographyTokenPath } from './shared.js';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

interface RawVariant {
  background?: unknown;
  content?: unknown;
  border?: unknown;
}
interface RawSize {
  height?: unknown;
  cornerRadius?: unknown;
  paddingHorizontal?: unknown;
  paddingVertical?: unknown;
  font?: unknown;
}
interface RawButtonDefinition {
  component?: {
    button?: {
      variants?: Record<string, RawVariant>;
      sizes?: Record<string, RawSize>;
      pressedInset?: unknown;
    };
  };
}

export interface ButtonVariant {
  name: string;
  /** Color token path, e.g. `semantic.color.action.primary`. */
  background: string;
  content: string;
  border?: string;
}
export interface ButtonSize {
  name: string;
  height: number;
  cornerRadius: number;
  paddingHorizontal: number;
  paddingVertical: number;
  /** Source font token id, e.g. `s1`. */
  fontToken: string;
  /** Grouped typography path, e.g. `typography.subtitle.s1`. */
  fontPath: string;
}
export interface ButtonDefinition {
  variants: ButtonVariant[];
  sizes: ButtonSize[];
  pressedInset: number;
}

function refPath(raw: unknown, where: string): string {
  if (typeof raw !== 'string') {
    throw new Error(`${where} must be a token reference string`);
  }
  const ref = parseReference(raw);
  if (!ref) {
    throw new Error(
      `${where} must be a token reference like {semantic.color.action.primary}`,
    );
  }
  return ref.join('.');
}

function num(raw: unknown, where: string): number {
  if (typeof raw !== 'number' || Number.isNaN(raw)) {
    throw new Error(`${where} must be a number`);
  }
  return raw;
}

export function loadButtonDefinition(jsonPath: string): ButtonDefinition {
  const raw = JSON.parse(
    readFileSync(resolve(ROOT, jsonPath), 'utf8'),
  ) as RawButtonDefinition;
  const button = raw.component?.button;
  if (!button?.variants || !button?.sizes) {
    throw new Error(
      `${jsonPath} must define component.button.variants and component.button.sizes`,
    );
  }

  const variants = Object.entries(button.variants).map(([name, v]) => ({
    name,
    background: refPath(v.background, `${jsonPath} → ${name}.background`),
    content: refPath(v.content, `${jsonPath} → ${name}.content`),
    border: v.border
      ? refPath(v.border, `${jsonPath} → ${name}.border`)
      : undefined,
  }));

  const sizes = Object.entries(button.sizes).map(([name, s]) => {
    const fontToken =
      typeof s.font === 'string' ? s.font : '';
    if (!fontToken) {
      throw new Error(`${jsonPath} → sizes.${name}.font must be a font token id`);
    }
    return {
      name,
      height: num(s.height, `${jsonPath} → sizes.${name}.height`),
      cornerRadius: num(s.cornerRadius, `${jsonPath} → sizes.${name}.cornerRadius`),
      paddingHorizontal: num(
        s.paddingHorizontal,
        `${jsonPath} → sizes.${name}.paddingHorizontal`,
      ),
      paddingVertical: num(
        s.paddingVertical,
        `${jsonPath} → sizes.${name}.paddingVertical`,
      ),
      fontToken,
      fontPath: typographyTokenPath(fontToken),
    };
  });

  return {
    variants,
    sizes,
    pressedInset:
      typeof button.pressedInset === 'number' ? button.pressedInset : 0,
  };
}

export interface ResolvedButtonStyle {
  /** Combined wire token, e.g. `component.button.primary.48`. */
  token: string;
  variant: string;
  size: string;
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

/** Cross-product of variants × sizes into combined, fully-specified styles. */
export function resolveButtonStyles(
  def: ButtonDefinition,
): ResolvedButtonStyle[] {
  const out: ResolvedButtonStyle[] = [];
  for (const variant of def.variants) {
    for (const size of def.sizes) {
      out.push({
        token: `component.button.${variant.name}.${size.name}`,
        variant: variant.name,
        size: size.name,
        background: variant.background,
        content: variant.content,
        border: variant.border ?? null,
        height: size.height,
        cornerRadius: size.cornerRadius,
        paddingHorizontal: size.paddingHorizontal,
        paddingVertical: size.paddingVertical,
        font: size.fontPath,
        pressedInset: def.pressedInset,
      });
    }
  }
  return out;
}

export function generateWebButtonJson(styles: ResolvedButtonStyle[]): string {
  const result: Record<string, Record<string, unknown>> = {};
  for (const style of styles) {
    const entry: Record<string, unknown> = {
      background: style.background,
      content: style.content,
      height: style.height,
      cornerRadius: style.cornerRadius,
      paddingHorizontal: style.paddingHorizontal,
      paddingVertical: style.paddingVertical,
      font: style.font,
      pressedInset: style.pressedInset,
    };
    if (style.border) {
      entry.border = style.border;
    }
    result[style.token] = entry;
  }
  return `${JSON.stringify(result, null, 2)}\n`;
}
