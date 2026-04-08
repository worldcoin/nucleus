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

const TEMPLATE = Handlebars.compile(
  readTemplate('src/templates/ios/NucleusColorsTemplate.swift.hbs'),
);

function hexOnly(hex: string): string {
  return hex.replace('#', '').toUpperCase();
}

function swiftColorValue(token: FormatFnArguments['dictionary']['allTokens'][number], enumName: string): string {
  const originalValue = originalTokenValue(token);
  const referencePath = referencedTokenPath(token);

  if (originalValue?.startsWith('{primitive.color.') && referencePath) {
    return `NucleusPrimitiveColors.${camelCasePath(publicColorPath(referencePath))}`;
  }

  if (originalValue?.startsWith('{semantic.color.') && referencePath) {
    return `${enumName}.${camelCasePath(publicColorPath(referencePath))}`;
  }

  return JSON.stringify(hexOnly(tokenValue(token)));
}

export const swiftColorDefaults: Format = {
  name: 'swift/nucleusColorDefaults',
  format: ({ dictionary, options }: FormatFnArguments) => {
    const enumName = (options.enumName as string) || 'NucleusPrimitiveColors';
    const tokens = colorTokens(dictionary)
      .map((token) => ({
        name: camelCasePath(publicColorPath(token.path)),
        value: swiftColorValue(token, enumName),
      }));

    return TEMPLATE({
      enumName,
      tokens,
    });
  },
};
