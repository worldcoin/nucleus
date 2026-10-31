import assert from 'node:assert/strict';
import test from 'node:test';

import { svgToVectorDrawable } from './icons-android.js';

function pathData(d: string): string {
  const xml = svgToVectorDrawable(`<svg viewBox="0 0 24 24"><path d="${d}"/></svg>`);
  return /android:pathData="([^"]+)"/.exec(xml)![1];
}

test('relative moves after close use the contour start, not its last vertex', () => {
  assert.equal(pathData('M5 16V19H8V21H3V16zm16 0v5h-5v-2h3v-3z'),
    'M5 16V19H8V21H3V16ZM21 16V21H16V19H19V16Z');
});

test('relative moves after open curves use the curve endpoint', () => {
  assert.equal(pathData('M12 14c-1 0-1 2 0 2s1-2 0-2m-1-1h2V8h-2z'),
    'M12 14C11 14 11 16 12 16S13 14 12 14M11 13H13V8H11Z');
});

test('initial relative moves, repeated coordinates, exponents and arcs retain SVG semantics', () => {
  assert.equal(pathData('m1e1 10 2 0a2 3 0 0 1 4 5z'),
    'M10 10L12 10A2 3 0 0 1 16 15Z');
});

test('malformed paths fail generation', () => {
  assert.throws(() => pathData('M1'), /Invalid svg path data/);
});

test('unoptimized Figma artboard rect clips match the optimized path form', () => {
  const svg = (clip: string) => `<svg viewBox="0 0 24 24"><g clip-path="url(#clip)"><path d="M2 2H22V22H2Z" fill="#1F1F1F"/></g><defs><clipPath id="clip">${clip}</clipPath></defs></svg>`;
  assert.equal(
    svgToVectorDrawable(svg('<rect width="24" height="24" fill="white"/>')),
    svgToVectorDrawable(svg('<path d="M0 0H24V24H0Z"/>')),
  );
  for (const rect of ['<rect width="20" height="24"/>', '<rect x="1" width="24" height="24"/>', '<rect width="24" height="24" rx="2"/>', '<rect width="24" height="24" transform="translate(1 0)"/>']) {
    assert.throws(() => svgToVectorDrawable(svg(rect)), /expected full artboard/);
  }
});
