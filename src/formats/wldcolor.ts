import Handlebars from 'handlebars';
import { readFileSync, writeFileSync, mkdirSync } from 'fs';
import { dirname } from 'path';

import { camelCasePath, readTemplate } from './shared.js';

const DEFAULTS_TEMPLATE = Handlebars.compile(
  readTemplate('src/templates/ios/WLDColorDefaultsTemplate.swift.hbs'),
);

interface PrimitiveToken {
  name: string;
  hex: string;
}

interface SemanticToken {
  name: string;
  lightHex: string;
  darkHex: string;
}

type TokenNode = { $value: string; $type: string } | { [key: string]: TokenNode };

function isLeafToken(node: TokenNode): node is { $value: string; $type: string } {
  return '$value' in node && '$type' in node;
}

function hexOnly(hex: string): string {
  return hex.replace('#', '').toUpperCase();
}

function flattenPrimitives(
  node: TokenNode,
  path: string[] = [],
): Map<string, string> {
  const result = new Map<string, string>();

  if (isLeafToken(node)) {
    if (node.$type === 'color') {
      result.set(path.join('.'), hexOnly(node.$value));
    }
    return result;
  }

  for (const [key, child] of Object.entries(node)) {
    for (const [childPath, value] of flattenPrimitives(child as TokenNode, [...path, key])) {
      result.set(childPath, value);
    }
  }

  return result;
}

function resolveValue(value: string, primitives: Map<string, string>): string {
  const refMatch = value.match(/^\{(.+)\}$/);
  if (refMatch) {
    const resolved = primitives.get(refMatch[1]);
    if (!resolved) {
      throw new Error(`Unresolved reference: ${value}`);
    }
    return resolved;
  }
  return hexOnly(value);
}

function collectSemanticTokens(
  node: TokenNode,
  primitives: Map<string, string>,
  path: string[] = [],
): Map<string, string> {
  const result = new Map<string, string>();

  if (isLeafToken(node)) {
    if (node.$type === 'color') {
      result.set(path.join('.'), resolveValue(node.$value, primitives));
    }
    return result;
  }

  for (const [key, child] of Object.entries(node)) {
    for (const [childPath, value] of collectSemanticTokens(child as TokenNode, primitives, [...path, key])) {
      result.set(childPath, value);
    }
  }

  return result;
}

export function generateWLDColorDefaults(
  primitivePath: string,
  semanticLightPath: string,
  semanticDarkPath: string,
  outputPath: string,
): void {
  const primitiveJson = JSON.parse(readFileSync(primitivePath, 'utf8')) as TokenNode;
  const semanticLightJson = JSON.parse(readFileSync(semanticLightPath, 'utf8')) as TokenNode;
  const semanticDarkJson = JSON.parse(readFileSync(semanticDarkPath, 'utf8')) as TokenNode;

  // flatten all primitives into a lookup map
  const primitiveMap = flattenPrimitives(primitiveJson);

  // collect primitive tokens for output
  const primitiveColorNode = (primitiveJson as Record<string, Record<string, TokenNode>>).primitive?.color;
  if (!primitiveColorNode) {
    throw new Error('No primitive.color found in primitive.json');
  }
  const flatPrimitiveColors = flattenPrimitives(primitiveColorNode);
  const primitiveTokens: PrimitiveToken[] = Array.from(flatPrimitiveColors.entries()).map(
    ([dotPath, hex]) => ({
      name: camelCasePath(dotPath.split('.')),
      hex,
    }),
  );

  // collect resolved semantic values for light and dark
  const semanticLightColorNode = (semanticLightJson as Record<string, Record<string, TokenNode>>).semantic?.color;
  const semanticDarkColorNode = (semanticDarkJson as Record<string, Record<string, TokenNode>>).semantic?.color;
  if (!semanticLightColorNode || !semanticDarkColorNode) {
    throw new Error('No semantic.color found in semantic JSON files');
  }

  const lightValues = collectSemanticTokens(semanticLightColorNode, primitiveMap);
  const darkValues = collectSemanticTokens(semanticDarkColorNode, primitiveMap);

  const semanticTokens: SemanticToken[] = Array.from(lightValues.entries()).map(
    ([dotPath, lightHex]) => {
      const name = camelCasePath(dotPath.split('.'));
      const darkHex = darkValues.get(dotPath);
      if (!darkHex) {
        throw new Error(`Missing dark value for semantic token: ${dotPath}`);
      }
      return { name, lightHex, darkHex };
    },
  );

  const output = DEFAULTS_TEMPLATE({ primitiveTokens, semanticTokens });

  mkdirSync(dirname(outputPath), { recursive: true });
  writeFileSync(outputPath, output);
}
