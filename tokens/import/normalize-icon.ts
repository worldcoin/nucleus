import { optimize, type CustomPlugin, type XastElement } from 'svgo';
import svgpath from 'svgpath';

// The convex hull of a Bezier's control points encloses the curve. A hull
// outside the viewport is inconclusive: retain that clip rather than guessing.
function insideViewport(node: XastElement): boolean {
  const a = node.attributes;
  if (
    [
      'transform',
      'filter',
      'mask',
      'style',
      'stroke',
      'marker-start',
      'marker-mid',
      'marker-end',
    ].some((k) => k in a)
  )
    return false;
  if (node.name === 'g' || node.name === 'svg')
    return node.children.every(
      (c) => c.type !== 'element' || insideViewport(c),
    );
  const inside = (x: number, y: number) =>
    Number.isFinite(x) &&
    Number.isFinite(y) &&
    x >= 0 &&
    y >= 0 &&
    x <= 24 &&
    y <= 24;
  if (node.name === 'rect') {
    const x = Number(a.x ?? 0),
      y = Number(a.y ?? 0);
    return inside(x, y) && inside(x + Number(a.width), y + Number(a.height));
  }
  if (node.name === 'circle') {
    const x = Number(a.cx ?? 0),
      y = Number(a.cy ?? 0),
      r = Number(a.r);
    return r >= 0 && inside(x - r, y - r) && inside(x + r, y + r);
  }
  if (node.name !== 'path' || !a.d) return false;
  let safe = true;
  svgpath(a.d)
    .abs()
    .unshort()
    .iterate((segment, _i, x, y) => {
      const [command, ...values] = segment;
      if (command === 'H') safe &&= inside(values[0]!, y);
      else if (command === 'V') safe &&= inside(x, values[0]!);
      else if (['M', 'L', 'C', 'Q'].includes(command)) {
        for (let i = 0; i < values.length; i += 2)
          safe &&= inside(values[i], values[i + 1]);
      } else if (command.toUpperCase() !== 'Z') safe = false;
    });
  return safe;
}

const removeRedundantArtboardClips: CustomPlugin = {
  name: 'removeRedundantArtboardClips',
  fn(root) {
    const elements: XastElement[] = [];
    const collect = (node: typeof root | XastElement) => {
      for (const c of node.children)
        if (c.type === 'element') {
          elements.push(c);
          collect(c);
        }
    };
    collect(root);
    const svg = elements.find((n) => n.name === 'svg');
    if (!svg || svg.attributes.viewBox !== '0 0 24 24') return;
    for (const clip of elements.filter((n) => n.name === 'clipPath')) {
      if (Object.keys(clip.attributes).some((k) => k !== 'id')) continue;
      const shapes = clip.children.filter((c) => c.type === 'element');
      if (shapes.length !== 1) continue;
      const shape = shapes[0],
        a = shape.attributes;
      const rect =
        shape.name === 'rect' &&
        Object.keys(a).every((k) =>
          ['width', 'height', 'x', 'y', 'fill'].includes(k),
        ) &&
        Number(a.x ?? 0) === 0 &&
        Number(a.y ?? 0) === 0 &&
        Number(a.width) === 24 &&
        Number(a.height) === 24;
      const path =
        shape.name === 'path' &&
        Object.keys(a).every((k) => ['d', 'fill'].includes(k)) &&
        svgpath(a.d ?? '')
          .abs()
          .toString()
          .replace(/[\s,]+/g, '')
          .toUpperCase() === 'M00H24V24H0Z';
      if (!rect && !path) continue;
      const reference = `url(#${clip.attributes.id})`;
      const targets = elements.filter(
        (n) => n.attributes['clip-path'] === reference,
      );
      // Inherited strokes/transforms can change bounds, so inspect ancestor state too.
      if (
        elements.some((n) =>
          ['transform', 'stroke', 'style', 'filter', 'mask'].some(
            (k) => k in n.attributes,
          ),
        )
      )
        continue;
      if (!targets.length || !targets.every(insideViewport)) continue;
      for (const target of targets) delete target.attributes['clip-path'];
    }
  },
};

export function canonicalSvg(svg: string): string {
  return (
    optimize(svg, {
      multipass: true,
      floatPrecision: 5,
      plugins: [removeRedundantArtboardClips, 'preset-default', 'sortAttrs'],
    }).data + '\n'
  );
}
