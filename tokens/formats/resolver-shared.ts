import { loadButtonDefinition, resolveButtonStyles } from './buttons.js';
import { discoverIconTokens, type IconVariant } from './icons-shared.js';
import { loadColorTokens, loadFontDefinitions } from './loaders.js';
import { camelCasePath, publicColorPath, typographyTokenPath } from './shared.js';

/**
 * Shared catalog for the token-path ↔ token resolvers. Enumerates every published token as a
 * `(canonical path string, platform accessor)` pair, reusing the exact same path-builders and
 * accessor-naming the platform token generators use, so the resolver can never drift from the
 * static accessors it points at. Consumed by `resolver-{web,ios,android}.ts`.
 */

const PRIMITIVE_SOURCE = 'tokens/definitions/color/primitive.json';
const SEMANTIC_LIGHT_SOURCE = 'tokens/definitions/color/semantic.light.json';
const FONT_SOURCE = 'tokens/definitions/font/fonts.json';
const BUTTON_SOURCE = 'tokens/definitions/component/button.json';

export interface ColorEntry {
  /** Canonical wire path, e.g. `semantic.color.text.primary`. */
  token: string;
  /** `NucleusColor` / `NucleusSemanticColors*` accessor, e.g. `textPrimary`. */
  accessor: string;
}

export interface FontEntry {
  /** Canonical wire path, e.g. `typography.subtitle.s3`. */
  token: string;
  /** `NucleusFont` / `NucleusFonts` accessor (bare id), e.g. `s3`. */
  accessor: string;
  /** camelCase web-constant key, e.g. `subtitleS3`. */
  key: string;
}

export interface ButtonEntry {
  /** Canonical wire path, e.g. `component.button.inverse.32`. */
  token: string;
  /** `NucleusButton` / `NucleusButtons` accessor, e.g. `inverse32`. Also the web-constant key. */
  accessor: string;
}

export interface IconEntry {
  /** Canonical wire path, e.g. `icon.arrow-right.regular`. */
  token: string;
  /** camelCase web-constant key, e.g. `arrowRightRegular`. */
  key: string;
  /** `NucleusIcon` Swift case, e.g. `arrowRight`. */
  swiftCase: string;
  /** `NucleusIcon` Kotlin object, e.g. `ArrowRight`. */
  kotlinCase: string;
  /** snake_case stem in android drawable names, e.g. `arrow_right`. */
  androidStem: string;
  variant: IconVariant;
}

export interface TokenCatalog {
  semanticColors: ColorEntry[];
  primitiveColors: ColorEntry[];
  fonts: FontEntry[];
  buttons: ButtonEntry[];
  icons: IconEntry[];
}

function colorEntries(source: string): ColorEntry[] {
  return loadColorTokens(source).map((leaf) => ({
    token: leaf.path.join('.'),
    accessor: camelCasePath(publicColorPath(leaf.path)),
  }));
}

function capitalize(value: string): string {
  return value.charAt(0).toUpperCase() + value.slice(1);
}

export function buildTokenCatalog(): TokenCatalog {
  // Light & dark semantic palettes share the same key paths; the light source is sufficient.
  const semanticColors = colorEntries(SEMANTIC_LIGHT_SOURCE);
  const primitiveColors = colorEntries(PRIMITIVE_SOURCE);

  const fonts: FontEntry[] = loadFontDefinitions(FONT_SOURCE).tokens.map((token) => {
    const path = typographyTokenPath(token.name); // typography.subtitle.s3
    return {
      token: path,
      accessor: token.name, // bare id (native), e.g. s3
      key: camelCasePath(path.split('.').slice(1)), // subtitleS3 (web)
    };
  });

  const buttons: ButtonEntry[] = resolveButtonStyles(
    loadButtonDefinition(BUTTON_SOURCE),
  ).map((style) => ({
    token: style.token, // component.button.inverse.32
    accessor: `${style.variant}${style.size}`, // inverse32
  }));

  const icons: IconEntry[] = discoverIconTokens().flatMap((icon) =>
    icon.variants.map((variant) => ({
      token: `icon.${icon.name}.${variant}`,
      key: `${icon.swiftCase}${capitalize(variant)}`, // arrowRightRegular
      swiftCase: icon.swiftCase,
      kotlinCase: icon.kotlinCase,
      androidStem: icon.androidStem,
      variant,
    })),
  );

  const catalog = { semanticColors, primitiveColors, fonts, buttons, icons };
  validateCatalog(catalog);
  return catalog;
}

/**
 * Generation-time round-trip guard: every canonical path is unique across the whole catalog and
 * every per-family web key is unique. (Wrong-accessor regressions are caught by the native builds
 * in CI; this catches path/key collisions and empty values before anything is written.)
 */
function validateCatalog(catalog: TokenCatalog): void {
  const tokens = [
    ...catalog.semanticColors.map((e) => e.token),
    ...catalog.primitiveColors.map((e) => e.token),
    ...catalog.fonts.map((e) => e.token),
    ...catalog.buttons.map((e) => e.token),
    ...catalog.icons.map((e) => e.token),
  ];
  const seenTokens = new Set<string>();
  for (const token of tokens) {
    if (!token) {
      throw new Error('resolver catalog: empty token path');
    }
    if (seenTokens.has(token)) {
      throw new Error(`resolver catalog: duplicate token path "${token}"`);
    }
    seenTokens.add(token);
  }

  const keyGroups: Array<[string, string[]]> = [
    ['ColorTokens', catalog.semanticColors.map((e) => e.accessor)],
    ['PrimitiveColorTokens', catalog.primitiveColors.map((e) => e.accessor)],
    ['TypographyTokens', catalog.fonts.map((e) => e.key)],
    ['ButtonTokens', catalog.buttons.map((e) => e.accessor)],
    ['IconTokens', catalog.icons.map((e) => e.key)],
  ];
  for (const [name, keys] of keyGroups) {
    const seen = new Set<string>();
    for (const key of keys) {
      if (!key) {
        throw new Error(`resolver catalog: empty key in ${name}`);
      }
      if (seen.has(key)) {
        throw new Error(`resolver catalog: duplicate key "${key}" in ${name}`);
      }
      seen.add(key);
    }
  }
}
