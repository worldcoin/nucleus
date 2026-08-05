import Handlebars from 'handlebars';

import { readTemplate } from './shared.js';
import type { IconToken, IconVariant } from './icons-shared.js';

const PACKAGE_NAME = 'com.worldcoin.nucleus.tokens';
const TEMPLATE = Handlebars.compile(
  readTemplate('tokens/templates/android/NucleusIcon.kt.hbs'),
);

// android resource names must be [a-z0-9_]; we prefix with `nucleus_icon_` so the
// drawables don't collide with whatever the consuming app already ships.
export const ANDROID_DRAWABLE_PREFIX = 'nucleus_icon';

export function androidDrawableName(token: IconToken, variant: IconVariant): string {
  return `${ANDROID_DRAWABLE_PREFIX}_${token.androidStem}_${variant}`;
}

// Kotlin marker interface + resource property generated for each variant an icon ships.
// Icons that don't ship a variant simply don't implement that interface, so accessing the
// missing variant's res property is a compile error in consuming code — no nullable res
// IDs and no runtime variant resolution.
const VARIANT_KOTLIN: Record<IconVariant, { markerInterface: string; property: string }> = {
  regular: { markerInterface: 'HasRegular', property: 'regularRes' },
  solid: { markerInterface: 'HasSolid', property: 'solidRes' },
};

interface AndroidIconEntry {
  kotlinCase: string;
  resourceName: string;
  // marker interfaces for the variants this icon ships, e.g. ['HasRegular', 'HasSolid']
  interfaces: string[];
  // one non-null res property override per shipped variant
  properties: Array<{ property: string; drawable: string }>;
}

function toAndroidEntry(token: IconToken): AndroidIconEntry {
  return {
    kotlinCase: token.kotlinCase,
    resourceName: token.name,
    interfaces: token.variants.map((variant) => VARIANT_KOTLIN[variant].markerInterface),
    properties: token.variants.map((variant) => ({
      property: VARIANT_KOTLIN[variant].property,
      drawable: `R.drawable.${androidDrawableName(token, variant)}`,
    })),
  };
}

export function generateAndroidIcons(tokens: IconToken[]): string {
  return TEMPLATE({ packageName: PACKAGE_NAME, tokens: tokens.map(toAndroidEntry) });
}

// SVG → VectorDrawable XML conversion. Our SVGs come straight from the Figma
// export: <path>/<rect>/<circle> shapes, optionally wrapped in a <g> that
// carries shared presentation attributes and a full-artboard clip defined in
// <defs>. Anything outside that shape is rejected loudly rather than being
// converted into a wrong-looking icon.

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

// presentation attributes that cascade from <svg>/<g> down to child shapes
const INHERITED_ATTRS = [
  'fill',
  'stroke',
  'fill-rule',
  'stroke-width',
  'stroke-linecap',
  'stroke-linejoin',
  'stroke-miterlimit',
] as const;

const STROKE_LINE_CAPS = new Set(['butt', 'round', 'square']);
const STROKE_LINE_JOINS = new Set(['miter', 'round', 'bevel']);

function mergeInherited(
  parent: Map<string, string>,
  attrs: Map<string, string>,
): Map<string, string> {
  const merged = new Map(parent);
  for (const name of INHERITED_ATTRS) {
    const value = attrs.get(name);
    if (value !== undefined) merged.set(name, value);
  }
  return merged;
}

interface DrawablePath {
  pathData: string;
  fill: boolean;
  fillType: 'evenOdd' | null;
  stroke: boolean;
  strokeWidth: string;
  strokeLineCap: string | null;
  strokeLineJoin: string | null;
  strokeMiterLimit: string | null;
}

function elementToDrawablePath(
  tag: string,
  attrs: Map<string, string>,
  inherited: Map<string, string>,
): DrawablePath | null {
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

  const attr = (name: string) => attrs.get(name) ?? inherited.get(name);

  const fill = attr('fill');
  const stroke = attr('stroke');
  // svg paints shapes black when no fill is declared anywhere in scope
  const filled = fill !== 'none';
  const stroked = stroke !== undefined && stroke !== 'none';

  const strokeLineCap = attr('stroke-linecap') ?? null;
  if (strokeLineCap !== null && !STROKE_LINE_CAPS.has(strokeLineCap)) {
    throw new Error(`Unsupported svg stroke-linecap: ${strokeLineCap}`);
  }
  const strokeLineJoin = attr('stroke-linejoin') ?? null;
  if (strokeLineJoin !== null && !STROKE_LINE_JOINS.has(strokeLineJoin)) {
    throw new Error(`Unsupported svg stroke-linejoin: ${strokeLineJoin}`);
  }

  return {
    pathData,
    fill: filled,
    fillType: filled && attr('fill-rule') === 'evenodd' ? 'evenOdd' : null,
    stroke: stroked,
    // svg strokes default to width 1, but android defaults to 0 (invisible)
    strokeWidth: attr('stroke-width') ?? '1',
    strokeLineCap,
    strokeLineJoin,
    strokeMiterLimit: attr('stroke-miterlimit') ?? null,
  };
}

// the figma export clips every icon to the artboard, which is a visual no-op;
// any other clip would change the icon's shape, so it has to fail the build
function assertArtboardClip(pathData: string, width: number, height: number): void {
  const normalized = pathData.replace(/[\s,]+/g, '').toUpperCase();
  if (normalized !== `M00H${width}V${height}H0Z`) {
    throw new Error(`Unsupported svg clipPath (expected full ${width}x${height} artboard): ${pathData}`);
  }
}

export function svgToVectorDrawable(svg: string): string {
  const svgRoot = /<svg([^>]*)>/.exec(svg);
  if (!svgRoot) throw new Error('Could not find <svg> root');
  const rootAttrs = parseAttrs(svgRoot[1]);
  const viewBox = rootAttrs.get('viewBox')?.split(/\s+/).map(Number);
  const width = viewBox?.[2] ?? Number(rootAttrs.get('width') ?? '24');
  const height = viewBox?.[3] ?? Number(rootAttrs.get('height') ?? '24');

  const tagRegex = /<(\/?)([a-zA-Z][\w-]*)([^>]*?)\/?>/g;
  const drawablePaths: DrawablePath[] = [];
  const inheritedStack: Array<Map<string, string>> = [new Map()];
  let defsDepth = 0;
  let clipPathDepth = 0;

  let match;
  while ((match = tagRegex.exec(svg)) !== null) {
    const closing = match[1] === '/';
    const tag = match[2];
    const attrs = closing ? new Map<string, string>() : parseAttrs(match[3]);
    const inherited = inheritedStack[inheritedStack.length - 1];

    switch (tag) {
      case 'svg':
      case 'g':
        if (closing) {
          inheritedStack.pop();
        } else {
          if (tag === 'g' && attrs.has('transform')) {
            throw new Error('Unsupported svg transform on <g>');
          }
          inheritedStack.push(mergeInherited(inherited, attrs));
        }
        break;
      case 'defs':
        defsDepth += closing ? -1 : 1;
        break;
      case 'clipPath':
        clipPathDepth += closing ? -1 : 1;
        break;
      case 'path':
      case 'rect':
      case 'circle': {
        if (defsDepth > 0) {
          if (clipPathDepth === 0 || tag !== 'path') {
            throw new Error(`Unsupported svg element in <defs>: <${tag}>`);
          }
          assertArtboardClip(attrs.get('d') ?? '', width, height);
          break;
        }
        const path = elementToDrawablePath(tag, attrs, inherited);
        if (path) drawablePaths.push(path);
        break;
      }
      default:
        throw new Error(`Unsupported svg element: <${tag}>`);
    }
  }

  if (drawablePaths.length === 0) {
    throw new Error('Svg produced no drawable paths');
  }

  // we hard-code black as the baseline color; consumers should tint via Compose
  // `Icon(painter, tint = ...)` (or `setTint` on a drawable in views)
  const placeholderColor = '#FF000000';

  const pathXml = drawablePaths
    .map((path) => {
      const lines: string[] = [`android:pathData="${path.pathData}"`];
      if (path.fill) {
        lines.push(`android:fillColor="${placeholderColor}"`);
        if (path.fillType) {
          lines.push(`android:fillType="${path.fillType}"`);
        }
      }
      if (path.stroke) {
        lines.push(`android:strokeColor="${placeholderColor}"`);
        lines.push(`android:strokeWidth="${path.strokeWidth}"`);
        if (path.strokeLineCap) {
          lines.push(`android:strokeLineCap="${path.strokeLineCap}"`);
        }
        if (path.strokeLineJoin) {
          lines.push(`android:strokeLineJoin="${path.strokeLineJoin}"`);
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
