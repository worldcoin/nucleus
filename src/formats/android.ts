import Handlebars from 'handlebars';
import type { Format, FormatFnArguments } from 'style-dictionary/types';

import {
  camelCasePath,
  colorTokens,
  originalTokenValue,
  publicColorPath,
  readTemplate,
  referencedTokenPath,
  tokenValue,
} from './shared.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus';
const TEMPLATE = Handlebars.compile(
  readTemplate('src/templates/android/NucleusColorsTemplate.kt.hbs'),
);

function hexToArgb(hex: string): string {
  const normalized = hex.replace('#', '').toUpperCase();

  if (normalized.length === 6) {
    return `0xFF${normalized}`;
  }

  if (normalized.length === 8) {
    return `0x${normalized.slice(6)}${normalized.slice(0, 6)}`;
  }

  throw new Error(`Unsupported color format: ${hex}`);
}

function androidColorValue(token: FormatFnArguments['dictionary']['allTokens'][number], objectName: string): string {
  const originalValue = originalTokenValue(token);
  const referencePath = referencedTokenPath(token);

  if (originalValue?.startsWith('{primitive.color.') && referencePath) {
    return `NucleusPrimitiveColors.${camelCasePath(publicColorPath(referencePath))}`;
  }

  if (originalValue?.startsWith('{semantic.color.') && referencePath) {
    return `${objectName}.${camelCasePath(publicColorPath(referencePath))}`;
  }

  return `Color(${hexToArgb(tokenValue(token))})`;
}

export const composeColorObject: Format = {
  name: 'compose/colorObject',
  format: ({ dictionary, options }: FormatFnArguments) => {
    const objectName = (options.objectName as string) || 'NucleusPrimitiveColors';
    const tokens = colorTokens(dictionary)
      .map((token) => ({
        name: camelCasePath(publicColorPath(token.path)),
        value: androidColorValue(token, objectName),
      }));

    return TEMPLATE({
      packageName: PACKAGE_NAME,
      objectName,
      tokens,
    });
  },
};
