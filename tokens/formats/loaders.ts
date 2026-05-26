import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

function readJson<T>(jsonPath: string): T {
  const absolute = resolve(ROOT, jsonPath);
  try {
    return JSON.parse(readFileSync(absolute, 'utf8')) as T;
  } catch (err) {
    throw new Error(`Failed to parse ${jsonPath}: ${(err as Error).message}`);
  }
}

// ---------- colors ----------

export interface ColorLeaf {
  path: string[];
  rawValue: string;
}

interface RawColorLeaf {
  $value?: unknown;
  $type?: unknown;
}

type RawColorTree = { [key: string]: RawColorLeaf | RawColorTree };

function isRawLeaf(node: unknown): node is RawColorLeaf {
  return typeof node === 'object' && node !== null && '$value' in (node as object);
}

function flattenColorTree(
  tree: RawColorTree,
  jsonPath: string,
  basePath: string[] = [],
): ColorLeaf[] {
  const out: ColorLeaf[] = [];
  for (const [key, value] of Object.entries(tree)) {
    const path = [...basePath, key];
    if (isRawLeaf(value)) {
      const raw = value.$value;
      if (typeof raw !== 'string') {
        throw new Error(
          `Invalid color token at ${path.join('.')} in ${jsonPath}: $value must be a string`,
        );
      }
      out.push({ path, rawValue: raw });
    } else {
      out.push(...flattenColorTree(value as RawColorTree, jsonPath, path));
    }
  }
  return out;
}

export function loadColorTokens(jsonPath: string): ColorLeaf[] {
  return flattenColorTree(readJson<RawColorTree>(jsonPath), jsonPath);
}

// ---------- fonts ----------

export interface FontFamily {
  key: string;
  file: string;
  postscriptName: string;
}

export interface FontToken {
  name: string;
  family: FontFamily;
  size: number;
  weight: number;
  letterSpacing: number;
  lineHeight: number;
  dynamicTypeStyle?: string;
}

export interface FontDefinitions {
  families: FontFamily[];
  tokens: FontToken[];
}

interface RawFontFamily {
  file?: unknown;
  postscriptName?: unknown;
}

interface RawFontToken {
  family?: unknown;
  size?: unknown;
  weight?: unknown;
  letterSpacing?: unknown;
  lineHeight?: unknown;
  dynamicTypeStyle?: unknown;
}

interface RawFontDefinitions {
  families?: Record<string, RawFontFamily>;
  tokens?: Record<string, RawFontToken>;
}

function requireString(value: unknown, where: string): string {
  if (typeof value !== 'string' || value.length === 0) {
    throw new Error(`${where} must be a non-empty string`);
  }
  return value;
}

function requireNumber(value: unknown, where: string): number {
  if (typeof value !== 'number' || Number.isNaN(value)) {
    throw new Error(`${where} must be a number`);
  }
  return value;
}

function parseFamily(key: string, raw: RawFontFamily, jsonPath: string): FontFamily {
  const where = `${jsonPath} → families.${key}`;
  return {
    key,
    file: requireString(raw.file, `${where}.file`),
    postscriptName: requireString(raw.postscriptName, `${where}.postscriptName`),
  };
}

function parseToken(
  name: string,
  raw: RawFontToken,
  familiesByKey: Map<string, FontFamily>,
  jsonPath: string,
): FontToken {
  const where = `${jsonPath} → tokens.${name}`;
  const familyKey = requireString(raw.family, `${where}.family`);
  const family = familiesByKey.get(familyKey);
  if (!family) {
    throw new Error(
      `${where}.family references unknown family "${familyKey}". Known: ${[...familiesByKey.keys()].join(', ')}`,
    );
  }
  return {
    name,
    family,
    size: requireNumber(raw.size, `${where}.size`),
    weight: requireNumber(raw.weight, `${where}.weight`),
    letterSpacing: requireNumber(raw.letterSpacing, `${where}.letterSpacing`),
    lineHeight: requireNumber(raw.lineHeight, `${where}.lineHeight`),
    dynamicTypeStyle:
      typeof raw.dynamicTypeStyle === 'string' ? raw.dynamicTypeStyle : undefined,
  };
}

export function loadFontDefinitions(jsonPath: string): FontDefinitions {
  const raw = readJson<RawFontDefinitions>(jsonPath);
  if (!raw.families || !raw.tokens) {
    throw new Error(`${jsonPath} must define both "families" and "tokens"`);
  }

  const families = Object.entries(raw.families).map(([key, value]) =>
    parseFamily(key, value, jsonPath),
  );
  const familiesByKey = new Map(families.map((f) => [f.key, f]));

  const tokens = Object.entries(raw.tokens).map(([name, value]) =>
    parseToken(name, value, familiesByKey, jsonPath),
  );

  return { families, tokens };
}
