import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import type { IconToken, IconVariant } from './icons-shared.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusIcons.kt.hbs'),
);

// android resource names must be [a-z0-9_]; we prefix with `nucleus_icon_` so the
// drawables don't collide with whatever the consuming app already ships.
export const ANDROID_DRAWABLE_PREFIX = 'nucleus_icon';

export function androidDrawableName(token: IconToken, variant: IconVariant): string {
  return `${ANDROID_DRAWABLE_PREFIX}_${token.androidStem}_${variant}`;
}

interface AndroidIconEntry {
  kotlinCase: string;
  resourceName: string;
  outline: string | null;
  regular: string | null;
  solid: string | null;
}

function toAndroidEntry(token: IconToken): AndroidIconEntry {
  const drawableFor = (variant: IconVariant): string | null =>
    token.variants.includes(variant) ? `R.drawable.${androidDrawableName(token, variant)}` : null;

  return {
    kotlinCase: token.kotlinCase,
    resourceName: token.name,
    outline: drawableFor('outline'),
    regular: drawableFor('regular'),
    solid: drawableFor('solid'),
  };
}

export function generateAndroidIcons(tokens: IconToken[]): string {
  return TEMPLATE({ packageName: PACKAGE_NAME, tokens: tokens.map(toAndroidEntry) });
}

// SVG → VectorDrawable XML conversion. Our SVGs are uniform: a single <svg>
// containing one or more <path>, <rect>, or <circle> children. The only
// transform we encounter is a `matrix(...)` on a single rect in `gift.svg`.

type Mat = readonly [number, number, number, number, number, number];
const IDENTITY: Mat = [1, 0, 0, 1, 0, 0];

function applyMat(mat: Mat, x: number, y: number): [number, number] {
  return [mat[0] * x + mat[2] * y + mat[4], mat[1] * x + mat[3] * y + mat[5]];
}

function parseTransform(value: string | undefined): Mat {
  if (!value) return IDENTITY;
  const match = /matrix\(\s*([^)]+)\)/.exec(value);
  if (!match) return IDENTITY;
  const parts = match[1].split(/[\s,]+/).map(Number);
  if (parts.length !== 6 || parts.some((n) => Number.isNaN(n))) {
    throw new Error(`Unsupported svg transform: ${value}`);
  }
  return [parts[0], parts[1], parts[2], parts[3], parts[4], parts[5]] as const;
}

function parseAttrs(raw: string): Map<string, string> {
  const map = new Map<string, string>();
  const attrRegex = /([a-zA-Z][\w-]*)\s*=\s*"([^"]*)"/g;
  let m;
  while ((m = attrRegex.exec(raw)) !== null) {
    map.set(m[1], m[2]);
  }
  return map;
}

function fmt(n: number): string {
  // tidy up floats so the generated XML stays readable
  return Number.isInteger(n) ? n.toString() : Number(n.toFixed(4)).toString();
}

function rectToPathData(attrs: Map<string, string>): string {
  const x = Number(attrs.get('x') ?? '0');
  const y = Number(attrs.get('y') ?? '0');
  const width = Number(attrs.get('width') ?? '0');
  const height = Number(attrs.get('height') ?? '0');
  const mat = parseTransform(attrs.get('transform'));

  const corners: Array<[number, number]> = [
    [x, y],
    [x + width, y],
    [x + width, y + height],
    [x, y + height],
  ];
  const transformed = corners.map(([cx, cy]) => applyMat(mat, cx, cy));

  const [p0, p1, p2, p3] = transformed;
  return `M${fmt(p0[0])},${fmt(p0[1])} L${fmt(p1[0])},${fmt(p1[1])} L${fmt(p2[0])},${fmt(p2[1])} L${fmt(p3[0])},${fmt(p3[1])} Z`;
}

function circleToPathData(attrs: Map<string, string>): string {
  const cx = Number(attrs.get('cx') ?? '0');
  const cy = Number(attrs.get('cy') ?? '0');
  const r = Number(attrs.get('r') ?? '0');
  const mat = parseTransform(attrs.get('transform'));

  // we only ever apply identity transforms to circles in our icon set, so
  // approximating with a single radius is fine
  const [centerX, centerY] = applyMat(mat, cx, cy);
  const start = centerX - r;
  return `M${fmt(start)},${fmt(centerY)} a${fmt(r)},${fmt(r)} 0 1,0 ${fmt(2 * r)},0 a${fmt(r)},${fmt(r)} 0 1,0 ${fmt(-2 * r)},0 Z`;
}

interface DrawablePath {
  pathData: string;
  fill: boolean;
  stroke: boolean;
  strokeWidth: string | null;
  strokeMiterLimit: string | null;
}

function elementToDrawablePath(tag: string, attrs: Map<string, string>): DrawablePath | null {
  let pathData: string;
  switch (tag) {
    case 'path':
      pathData = attrs.get('d') ?? '';
      break;
    case 'rect':
      pathData = rectToPathData(attrs);
      break;
    case 'circle':
      pathData = circleToPathData(attrs);
      break;
    default:
      throw new Error(`Unsupported svg element: <${tag}>`);
  }
  if (!pathData) return null;

  const fill = attrs.get('fill');
  const stroke = attrs.get('stroke');

  return {
    pathData,
    fill: fill !== undefined && fill !== 'none',
    stroke: stroke !== undefined && stroke !== 'none',
    strokeWidth: attrs.get('stroke-width') ?? null,
    strokeMiterLimit: attrs.get('stroke-miterlimit') ?? null,
  };
}

export function svgToVectorDrawable(svg: string): string {
  const svgRoot = /<svg([^>]*)>/.exec(svg);
  if (!svgRoot) throw new Error('Could not find <svg> root');
  const rootAttrs = parseAttrs(svgRoot[1]);
  const viewBox = rootAttrs.get('viewBox')?.split(/\s+/).map(Number) ?? [0, 0, 24, 24];
  const width = viewBox[2];
  const height = viewBox[3];

  const elementRegex = /<(path|rect|circle)\b([^/>]*)\/?>/g;
  const drawablePaths: DrawablePath[] = [];
  let match;
  while ((match = elementRegex.exec(svg)) !== null) {
    const tag = match[1];
    const attrs = parseAttrs(match[2]);
    const path = elementToDrawablePath(tag, attrs);
    if (path) drawablePaths.push(path);
  }

  // we hard-code black as the baseline color; consumers should tint via Compose
  // `Icon(painter, tint = ...)` (or `setTint` on a drawable in views)
  const placeholderColor = '#FF000000';

  const pathXml = drawablePaths
    .map((path) => {
      const lines: string[] = [`android:pathData="${path.pathData}"`];
      if (path.fill) {
        lines.push(`android:fillColor="${placeholderColor}"`);
      }
      if (path.stroke) {
        lines.push(`android:strokeColor="${placeholderColor}"`);
        if (path.strokeWidth) {
          lines.push(`android:strokeWidth="${path.strokeWidth}"`);
        }
        if (path.strokeMiterLimit) {
          lines.push(`android:strokeMiterLimit="${path.strokeMiterLimit}"`);
        }
      }
      return `    <path\n        ${lines.join('\n        ')} />`;
    })
    .join('\n');

  return `<?xml version="1.0" encoding="utf-8"?>
<vector xmlns:android="http://schemas.android.com/apk/res/android"
    android:width="${width}dp"
    android:height="${height}dp"
    android:viewportWidth="${width}"
    android:viewportHeight="${height}">
${pathXml}
</vector>
`;
}
