import { type IconToken, type IconVariant, ICON_VARIANTS, iconAssetBaseName, readIconSvg } from './icons-shared.js';

export interface WebIconEntry {
  name: string;
  // map of variant → relative path to the svg file (relative to the package root)
  files: Partial<Record<IconVariant, string>>;
  // map of variant → inlined svg source (handy for direct-paste use)
  svg: Partial<Record<IconVariant, string>>;
}

const ICON_FOLDER = 'icons';

// source icons are drawn in a fixed ink color; web assets swap it for
// currentColor so icons inherit the surrounding CSS `color` (e.g. dark mode)
const ICON_INK_PATTERN = /(fill|stroke)="#1f1f1f"/gi;

export function iconSvgForWeb(svg: string): string {
  return svg.replace(ICON_INK_PATTERN, '$1="currentColor"');
}

export function planWebIcons(tokens: IconToken[]): WebIconEntry[] {
  return tokens.map((token) => {
    const files: Partial<Record<IconVariant, string>> = {};
    const svg: Partial<Record<IconVariant, string>> = {};

    for (const variant of ICON_VARIANTS) {
      if (!token.variants.includes(variant)) continue;
      const filename = `${iconAssetBaseName(token.name, variant)}.svg`;
      files[variant] = `${ICON_FOLDER}/${filename}`;
      svg[variant] = iconSvgForWeb(readIconSvg(variant, token.name).trim());
    }

    return { name: token.name, files, svg };
  });
}

export function generateWebIconsManifest(tokens: IconToken[]): string {
  const entries = planWebIcons(tokens);
  const indexedByName: Record<string, Pick<WebIconEntry, 'files' | 'svg'>> = {};
  for (const entry of entries) {
    indexedByName[entry.name] = { files: entry.files, svg: entry.svg };
  }
  return `${JSON.stringify(indexedByName, null, 2)}\n`;
}
