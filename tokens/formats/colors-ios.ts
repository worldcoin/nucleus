import Handlebars from 'handlebars';

import { camelCasePath, parseReference, publicColorPath, readTemplate } from './shared.js';
import { loadColorTokens } from './loaders.js';
import type { ColorLeaf } from './loaders.js';

const PRIMITIVES_TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusColor+Primitives.swift.hbs'),
);
const SEMANTICS_TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusColor+Semantics.swift.hbs'),
);

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

function hexOnly(value: string): string {
  return value.replace('#', '').toUpperCase();
}

function resolveHex(rawValue: string, primitivesByPath: Map<string, string>): string {
  const referencePath = parseReference(rawValue);
  if (!referencePath) return hexOnly(rawValue);
  const resolved = primitivesByPath.get(referencePath.join('.'));
  if (!resolved) {
    throw new Error(`Unresolved nucleus color reference: ${rawValue}`);
  }
  return resolved;
}

function buildPrimitives(tokens: ColorLeaf[]): {
  entries: PrimitiveEntry[];
  byPath: Map<string, string>;
} {
  const byPath = new Map<string, string>();
  const entries: PrimitiveEntry[] = [];
  for (const { path, rawValue } of tokens) {
    const hex = hexOnly(rawValue);
    byPath.set(path.join('.'), hex);
    entries.push({ name: camelCasePath(publicColorPath(path)), hex });
  }
  entries.sort((a, b) => a.name.localeCompare(b.name));
  return { entries, byPath };
}

function buildSemantics(
  lightTokens: ColorLeaf[],
  darkTokens: ColorLeaf[],
  primitivesByPath: Map<string, string>,
): SemanticEntry[] {
  const collectByName = (tokens: ColorLeaf[]): Map<string, string> => {
    const out = new Map<string, string>();
    for (const { path, rawValue } of tokens) {
      out.set(camelCasePath(publicColorPath(path)), resolveHex(rawValue, primitivesByPath));
    }
    return out;
  };

  const lightByName = collectByName(lightTokens);
  const darkByName = collectByName(darkTokens);

  const allNames = new Set([...lightByName.keys(), ...darkByName.keys()]);
  const entries: SemanticEntry[] = [];
  for (const name of allNames) {
    const lightHex = lightByName.get(name);
    const darkHex = darkByName.get(name);
    if (!lightHex || !darkHex) {
      throw new Error(`Semantic token '${name}' missing from one of the light/dark sources`);
    }
    entries.push({ name, lightHex, darkHex, sameForBoth: lightHex === darkHex });
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
  const primitiveTokens = loadColorTokens(args.primitiveSource);
  const lightTokens = loadColorTokens(args.semanticLightSource);
  const darkTokens = loadColorTokens(args.semanticDarkSource);

  const { entries: primitiveEntries, byPath: primitivesByPath } = buildPrimitives(primitiveTokens);
  const semanticEntries = buildSemantics(lightTokens, darkTokens, primitivesByPath);

  return {
    primitivesContent: PRIMITIVES_TEMPLATE({ tokens: primitiveEntries }),
    semanticsContent: SEMANTICS_TEMPLATE({ tokens: semanticEntries }),
  };
}
