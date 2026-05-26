import Handlebars from 'handlebars';

import {
  camelCasePath,
  parseReference,
  publicColorPath,
  readTemplate,
} from './shared.js';
import type { ColorLeaf } from './loaders.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusColors.kt.hbs'),
);

function hexToArgb(hex: string): string {
  const normalized = hex.replace('#', '').toUpperCase();
  if (normalized.length === 6) return `0xFF${normalized}`;
  if (normalized.length === 8) return `0x${normalized.slice(6)}${normalized.slice(0, 6)}`;
  throw new Error(`Unsupported color format: ${hex}`);
}

function androidColorValue(rawValue: string, semanticObjectName: string): string {
  const referencePath = parseReference(rawValue);
  if (referencePath) {
    const [root] = referencePath;
    const accessor = camelCasePath(publicColorPath(referencePath));
    if (root === 'primitive') return `NucleusPrimitiveColors.${accessor}`;
    if (root === 'semantic') return `${semanticObjectName}.${accessor}`;
  }
  return `Color(${hexToArgb(rawValue)})`;
}

interface RenderArgs {
  objectName: string;
  tokens: ColorLeaf[];
  semanticObjectName: string;
}

function render({ objectName, tokens, semanticObjectName }: RenderArgs): string {
  return TEMPLATE({
    packageName: PACKAGE_NAME,
    objectName,
    tokens: tokens.map((token) => ({
      name: camelCasePath(publicColorPath(token.path)),
      value: androidColorValue(token.rawValue, semanticObjectName),
    })),
  });
}

export function generateAndroidPrimitiveColors(tokens: ColorLeaf[]): string {
  return render({
    objectName: 'NucleusPrimitiveColors',
    tokens,
    semanticObjectName: 'NucleusPrimitiveColors',
  });
}

export function generateAndroidSemanticColors(
  objectName: string,
  tokens: ColorLeaf[],
): string {
  return render({ objectName, tokens, semanticObjectName: objectName });
}
