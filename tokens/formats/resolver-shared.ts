import { loadButtonDefinition, resolveButtonStyles } from './buttons.js';
import { discoverIconTokens, type IconVariant } from './icons-shared.js';
import { loadColorTokens, loadFontDefinitions } from './loaders.js';
import { camelCasePath, publicColorPath } from './shared.js';

/**
 * Shared catalog for the token-path ↔ token resolvers. Enumerates every published token with both
 * its canonical path (for reference) and the **type-scoped wire token** the backend emits and the
 * resolvers key on — the namespace that the resolver type already implies is stripped:
 *   - color:      `semantic.color.foreground.primary` → `foreground.primary`  (and `primitive.color.grey.900` → `grey.900`)
 *   - typography: referenced directly by name, e.g. `s3`
 *   - button:     `component.button.inverse.32`  → `inverse.32`
 *   - icon:       `icon.arrow-right.regular`      → `arrow-right.regular`
 * Reuses the same accessor-naming the platform token generators use, so the resolver can't drift.
 */

const PRIMITIVE_SOURCE = 'tokens/definitions/color/primitive.json';
const SEMANTIC_LIGHT_SOURCE = 'tokens/definitions/color/semantic.light.json';
const FONT_SOURCE = 'tokens/definitions/font/fonts.json';
const BUTTON_SOURCE = 'tokens/definitions/component/button.json';

export interface ColorEntry {
  /** Type-scoped wire token, e.g. `text.primary` / `grey.900`. */
  wireToken: string;
  /** `NucleusColor` / `NucleusSemanticColors*` accessor, e.g. `foregroundPrimary`. */
  accessor: string;
}

export interface FontEntry {
  /** Wire token (bare id), e.g. `s3`. Also the `NucleusFont(s)` accessor and web-constant key. */
  wireToken: string;
  /** web-constant key, e.g. `s3`. */
  key: string;
}

export interface ButtonEntry {
  /** Type-scoped wire token, e.g. `inverse.32`. */
  wireToken: string;
  /** `NucleusButton(s)` accessor + web-constant key, e.g. `inverse32`. */
  accessor: string;
}

export interface IconEntry {
  /** Type-scoped wire token, e.g. `arrow-right.regular`. */
  wireToken: string;
  /** camelCase web-constant key, e.g. `arrowRightRegular`. */
  key: string;
  /** kebab-case icon name, e.g. `arrow-right`. */
  name: string;
  /** `NucleusIcon` Swift case, e.g. `arrowRight`. */
  swiftCase: string;
  /** `NucleusIcon` Kotlin object, e.g. `ArrowRight`. */
  kotlinCase: string;
  /** snake_case stem in android drawable names, e.g. `arrow_right`. */
  androidStem: string;
  variant: IconVariant;
}

export interface IconNameEntry {
  /** kebab-case wire name, e.g. `arrow-right`. */
  name: string;
  swiftCase: string;
  kotlinCase: string;
  androidStem: string;
}

export interface TokenCatalog {
  semanticColors: ColorEntry[];
  primitiveColors: ColorEntry[];
  fonts: FontEntry[];
  buttons: ButtonEntry[];
  icons: IconEntry[];
  /** Distinct icon names (for the iOS name → NucleusIcon map). */
  iconNames: IconNameEntry[];
  /** Icon variants present across the set (for the iOS variant map). */
  iconVariants: IconVariant[];
}

function colorEntries(source: string): ColorEntry[] {
  return loadColorTokens(source).map((leaf) => ({
    wireToken: publicColorPath(leaf.path).join('.'),
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

  const fonts: FontEntry[] = loadFontDefinitions(FONT_SOURCE).tokens.map((token) => ({
    wireToken: token.name, // bare id, e.g. s3
    key: token.name, // s3 (web)
  }));

  const buttons: ButtonEntry[] = resolveButtonStyles(
    loadButtonDefinition(BUTTON_SOURCE),
  ).map((style) => ({
    wireToken: `${style.variant}.${style.size}`, // inverse.32
    accessor: `${style.variant}${style.size}`, // inverse32
  }));

  const discovered = discoverIconTokens();
  const icons: IconEntry[] = discovered.flatMap((icon) =>
    icon.variants.map((variant) => ({
      wireToken: `${icon.name}.${variant}`, // arrow-right.regular
      key: `${icon.swiftCase}${capitalize(variant)}`, // arrowRightRegular
      name: icon.name,
      swiftCase: icon.swiftCase,
      kotlinCase: icon.kotlinCase,
      androidStem: icon.androidStem,
      variant,
    })),
  );
  const iconNames: IconNameEntry[] = discovered.map((icon) => ({
    name: icon.name,
    swiftCase: icon.swiftCase,
    kotlinCase: icon.kotlinCase,
    androidStem: icon.androidStem,
  }));
  const iconVariants = [...new Set(discovered.flatMap((icon) => icon.variants))].sort();

  const catalog = {
    semanticColors,
    primitiveColors,
    fonts,
    buttons,
    icons,
    iconNames,
    iconVariants,
  };
  validateCatalog(catalog);
  return catalog;
}

/**
 * Generation-time round-trip guard: the wire tokens a single resolver keys on must be unique
 * (colors merge semantic + primitive into one `NucleusColor` map), and every per-family web key
 * must be unique. (Wrong-accessor regressions are caught by the native builds in CI.)
 */
function validateCatalog(catalog: TokenCatalog): void {
  const groups: Array<[string, string[]]> = [
    // NucleusColor.resolve keys on one merged map → must be unique across semantic + primitive.
    [
      'color wire tokens',
      [...catalog.semanticColors, ...catalog.primitiveColors].map((e) => e.wireToken),
    ],
    ['font wire tokens', catalog.fonts.map((e) => e.wireToken)],
    ['button wire tokens', catalog.buttons.map((e) => e.wireToken)],
    ['icon wire tokens', catalog.icons.map((e) => e.wireToken)],
    ['ColorTokens keys', catalog.semanticColors.map((e) => e.accessor)],
    ['PrimitiveColorTokens keys', catalog.primitiveColors.map((e) => e.accessor)],
    ['TypographyTokens keys', catalog.fonts.map((e) => e.key)],
    ['ButtonTokens keys', catalog.buttons.map((e) => e.accessor)],
    ['IconTokens keys', catalog.icons.map((e) => e.key)],
  ];
  for (const [label, values] of groups) {
    const seen = new Set<string>();
    for (const value of values) {
      if (!value) {
        throw new Error(`resolver catalog: empty value in ${label}`);
      }
      if (seen.has(value)) {
        throw new Error(`resolver catalog: duplicate "${value}" in ${label}`);
      }
      seen.add(value);
    }
  }
}
