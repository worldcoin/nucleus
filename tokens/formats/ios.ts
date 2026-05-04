import Handlebars from 'handlebars';
import { readFileSync } from 'fs';
import { resolve, dirname } from 'path';
import { fileURLToPath } from 'url';

import { camelCasePath, publicColorPath, readTemplate } from './shared.js';

const ROOT = resolve(dirname(fileURLToPath(import.meta.url)), '../..');

const PRIMITIVES_TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusColorPrimitives.swift.hbs'),
);
const SEMANTICS_TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusColorSemantics.swift.hbs'),
);

interface ColorLeaf {
  $value: string;
  $type?: string;
}

type ColorTree = { [key: string]: ColorLeaf | ColorTree };

interface FlatToken {
  path: string[];
  rawValue: string;
}

function isLeaf(node: unknown): node is ColorLeaf {
  return typeof node === 'object' && node !== null && '$value' in node;
}

function flattenTree(tree: ColorTree, basePath: string[] = []): FlatToken[] {
  const out: FlatToken[] = [];
  for (const [key, value] of Object.entries(tree)) {
    const path = [...basePath, key];
    if (isLeaf(value)) {
      out.push({ path, rawValue: value.$value });
    } else {
      out.push(...flattenTree(value as ColorTree, path));
    }
  }
  return out;
}

function loadFlattenedTokens(jsonPath: string): FlatToken[] {
  const content = JSON.parse(readFileSync(resolve(ROOT, jsonPath), 'utf8')) as ColorTree;
  return flattenTree(content);
}

function hexOnly(value: string): string {
  return value.replace('#', '').toUpperCase();
}

function resolveHex(rawValue: string, primitivesByPath: Map<string, string>): string {
  const referenceMatch = rawValue.match(/^\{(.+)\}$/);
  if (!referenceMatch) {
    return hexOnly(rawValue);
  }
  const referencePath = referenceMatch[1];
  const resolved = primitivesByPath.get(referencePath);
  if (!resolved) {
    throw new Error(`Unresolved nucleus color reference: ${rawValue}`);
  }
  return resolved;
}

interface PrimitiveEntry {
  name: string;
  hex: string;
}

interface SemanticEntry {
  name: string;
  lightHex: string;
  darkHex: string;
  sameForBoth: boolean;
}

function buildPrimitives(jsonPath: string): {
  entries: PrimitiveEntry[];
  byPath: Map<string, string>;
} {
  const tokens = loadFlattenedTokens(jsonPath);
  const byPath = new Map<string, string>();
  const entries: PrimitiveEntry[] = [];
  for (const { path, rawValue } of tokens) {
    const hex = hexOnly(rawValue);
    byPath.set(path.join('.'), hex);
    entries.push({
      name: camelCasePath(publicColorPath(path)),
      hex,
    });
  }
  entries.sort((a, b) => a.name.localeCompare(b.name));
  return { entries, byPath };
}

function buildSemantics(
  lightJsonPath: string,
  darkJsonPath: string,
  primitivesByPath: Map<string, string>,
): SemanticEntry[] {
  const collectByName = (jsonPath: string): Map<string, string> => {
    const tokens = loadFlattenedTokens(jsonPath);
    const out = new Map<string, string>();
    for (const { path, rawValue } of tokens) {
      out.set(camelCasePath(publicColorPath(path)), resolveHex(rawValue, primitivesByPath));
    }
    return out;
  };

  const lightByName = collectByName(lightJsonPath);
  const darkByName = collectByName(darkJsonPath);

  const allNames = new Set([...lightByName.keys(), ...darkByName.keys()]);
  const entries: SemanticEntry[] = [];
  for (const name of allNames) {
    const lightHex = lightByName.get(name);
    const darkHex = darkByName.get(name);
    if (!lightHex || !darkHex) {
      throw new Error(`Semantic token '${name}' missing from one of the light/dark sources`);
    }
    entries.push({
      name,
      lightHex,
      darkHex,
      sameForBoth: lightHex === darkHex,
    });
  }
  entries.sort((a, b) => a.name.localeCompare(b.name));
  return entries;
}

export interface IOSColorOutput {
  primitivesContent: string;
  semanticsContent: string;
}

export function generateIOSColors(args: {
  primitiveSource: string;
  semanticLightSource: string;
  semanticDarkSource: string;
}): IOSColorOutput {
  const { entries: primitiveEntries, byPath: primitivesByPath } = buildPrimitives(args.primitiveSource);
  const semanticEntries = buildSemantics(
    args.semanticLightSource,
    args.semanticDarkSource,
    primitivesByPath,
  );

  return {
    primitivesContent: PRIMITIVES_TEMPLATE({ tokens: primitiveEntries }),
    semanticsContent: SEMANTICS_TEMPLATE({ tokens: semanticEntries }),
  };
}
