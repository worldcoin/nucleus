import { mkdirSync, writeFileSync } from 'fs';
import { resolve } from 'path';

import { buildTokenCatalog } from '../formats/resolver-shared.js';
import { generateWebTypes } from '../formats/types-web.js';
import { ROOT, WEB_OUT, logStage } from './shared.js';

function writeOut(relPath: string, content: string): void {
  const absolute = resolve(ROOT, relPath);
  mkdirSync(resolve(absolute, '..'), { recursive: true });
  writeFileSync(absolute, content);
}

/**
 * Generate the web package's `index.d.ts` literal-union types. The members are sourced from the
 * shared token catalog (`buildTokenCatalog`) — the exact same `wireToken` values that drive the
 * `token-paths` constants and the native resolvers — so the union types stay in lockstep with the
 * runtime constants and the resolver keys.
 */
export function buildWebTypes(): void {
  const catalog = buildTokenCatalog();

  const out = `${WEB_OUT}/index.d.ts`;
  writeOut(
    out,
    generateWebTypes({
      colorTokens: [...catalog.semanticColors, ...catalog.primitiveColors].map(
        (c) => c.wireToken,
      ),
      typographyTokens: catalog.fonts.map((f) => f.wireToken),
      buttonStyleTokens: catalog.buttons.map((b) => b.wireToken),
      iconTokens: catalog.icons.map((i) => i.wireToken),
    }),
  );

  logStage('token types (web)', [['web', out]]);
}
