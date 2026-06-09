import { readdirSync, readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

export const ICON_DEFINITIONS_DIR = 'tokens/definitions/icons';
export const ICON_VARIANTS = ['outline', 'regular', 'solid'] as const;
export type IconVariant = (typeof ICON_VARIANTS)[number];

export interface IconToken {
  // kebab-case identifier — also the asset filename and Swift raw value
  name: string;
  // camelCase Swift case name
  swiftCase: string;
  // PascalCase Kotlin enum entry name
  kotlinCase: string;
  // snake_case stem used in android resource filenames
  androidStem: string;
  // variants this icon ships in (subset of ICON_VARIANTS)
  variants: IconVariant[];
}

function kebabToCamel(name: string): string {
  return name.replace(/-([a-z0-9])/g, (_, c: string) => c.toUpperCase());
}

function kebabToPascal(name: string): string {
  const camel = kebabToCamel(name);
  return camel.charAt(0).toUpperCase() + camel.slice(1);
}

function kebabToSnake(name: string): string {
  return name.replace(/-/g, '_');
}

export function discoverIconTokens(): IconToken[] {
  const seen = new Map<string, Set<IconVariant>>();

  for (const variant of ICON_VARIANTS) {
    const dir = resolve(ROOT, ICON_DEFINITIONS_DIR, variant);
    const files = readdirSync(dir).filter((file) => file.toLowerCase().endsWith('.svg'));
    for (const file of files) {
      const name = file.replace(/\.svg$/i, '');
      let variants = seen.get(name);
      if (!variants) {
        variants = new Set();
        seen.set(name, variants);
      }
      variants.add(variant);
    }
  }

  return [...seen.entries()]
    .sort(([a], [b]) => a.localeCompare(b))
    .map(([name, variants]) => ({
      name,
      swiftCase: kebabToCamel(name),
      kotlinCase: kebabToPascal(name),
      androidStem: kebabToSnake(name),
      variants: ICON_VARIANTS.filter((variant) => variants.has(variant)),
    }));
}

export function readIconSvg(variant: IconVariant, name: string): string {
  const path = resolve(ROOT, ICON_DEFINITIONS_DIR, variant, `${name}.svg`);
  return readFileSync(path, 'utf8');
}

export function iconAssetBaseName(name: string, variant: IconVariant): string {
  return `${name}-${variant}`;
}
