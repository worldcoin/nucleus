import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import {
  ICON_VARIANTS,
  iconAssetBaseName,
  type IconToken,
  type IconVariant,
} from './icons-shared.js';

const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/ios/NucleusIcon+Symbol.swift.hbs'),
);

interface IOSIconEntry {
  name: string;
  swiftCase: string;
  availableVariantsSwift: string;
  availableVariantsHuman: string;
}

function toIOSEntry(token: IconToken): IOSIconEntry {
  const cases = token.variants.map((variant) => `.${variant}`).join(', ');
  const human = token.variants.join(', ');

  return {
    name: token.name,
    swiftCase: token.swiftCase,
    availableVariantsSwift: cases,
    availableVariantsHuman: human,
  };
}

export function generateIOSIcons(tokens: IconToken[]): string {
  return TEMPLATE({ tokens: tokens.map(toIOSEntry) });
}

// xcassets generation

interface ImagesetContents {
  images: Array<{
    filename: string;
    idiom: 'universal';
  }>;
  info: {
    author: 'xcode';
    version: 1;
  };
  properties: {
    'preserves-vector-representation': true;
    'template-rendering-intent': 'template';
  };
}

interface CatalogContents {
  info: {
    author: 'xcode';
    version: 1;
  };
}

export function imagesetContentsJson(filename: string): string {
  const contents: ImagesetContents = {
    images: [{ filename, idiom: 'universal' }],
    info: { author: 'xcode', version: 1 },
    properties: {
      'preserves-vector-representation': true,
      'template-rendering-intent': 'template',
    },
  };
  return `${JSON.stringify(contents, null, 2)}\n`;
}

export function catalogContentsJson(): string {
  const contents: CatalogContents = {
    info: { author: 'xcode', version: 1 },
  };
  return `${JSON.stringify(contents, null, 2)}\n`;
}

export interface IOSImagesetPlan {
  name: string; // imageset directory name without extension
  variant: IconVariant;
  iconName: string; // kebab-case icon name (used to locate svg source)
}

export function planIOSImagesets(tokens: IconToken[]): IOSImagesetPlan[] {
  const plans: IOSImagesetPlan[] = [];
  for (const token of tokens) {
    for (const variant of ICON_VARIANTS) {
      if (!token.variants.includes(variant)) continue;
      plans.push({
        name: iconAssetBaseName(token.name, variant),
        variant,
        iconName: token.name,
      });
    }
  }
  return plans;
}
