import type { Format, FormatFnArguments } from 'style-dictionary/types';

import {
  camelCasePath,
  colorTokens,
  kebabCasePath,
  originalTokenValue,
  publicColorPath,
  referencedTokenPath,
  tokenValue,
} from './shared.js';

function cssVariableName(path: string[]): string {
  return `--nucleus-${kebabCasePath(publicColorPath(path))}`;
}

function cssColorValue(token: FormatFnArguments['dictionary']['allTokens'][number]): string {
  const referencePath = referencedTokenPath(token);

  if (referencePath) {
    return `var(${cssVariableName(referencePath)})`;
  }

  return tokenValue(token);
}

export const cssColorVariables: Format = {
  name: 'css/colorVariables',
  format: ({ dictionary }: FormatFnArguments) => {
    const lines = colorTokens(dictionary).map((token) => (
      `  ${cssVariableName(token.path)}: ${cssColorValue(token)};`
    ));

    return [
      ':root {',
      ...lines,
      '}',
      '',
    ].join('\n');
  },
};
export const jsonFlat: Format = {
  name: 'json/flat',
  format: ({ dictionary }: FormatFnArguments) => {
    const result: Record<string, unknown> = {};

    for (const token of dictionary.allTokens) {
      result[camelCasePath(publicColorPath(token.path))] = originalTokenValue(token) ?? tokenValue(token);
    }

    return JSON.stringify(result, null, 2) + '\n';
  },
};
