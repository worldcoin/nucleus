import { readFileSync } from 'node:fs';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { parseArgs } from 'node:util';
import { importBundle } from './icon-bundle.js';

const { values, positionals } = parseArgs({
  allowPositionals: true,
  options: { write: { type: 'boolean' } },
});
if (positionals.length !== 1)
  throw new Error('Usage: npm run icons:import -- bundle.json [--write]');
const directory = resolve(
  dirname(fileURLToPath(import.meta.url)),
  '../definitions/icons',
);
console.log(
  JSON.stringify(
    importBundle(
      JSON.parse(readFileSync(resolve(positionals[0]), 'utf8')),
      directory,
      values.write,
    ),
    null,
    2,
  ),
);
