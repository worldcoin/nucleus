import { readFileSync } from 'node:fs';
import { dirname, resolve } from 'node:path';
import { fileURLToPath } from 'node:url';
import { parseArgs } from 'node:util';
import { importBundle } from './icon-bundle.js';

try {
  const { values, positionals } = parseArgs({
    allowPositionals: true,
    options: { write: { type: 'boolean' }, strict: { type: 'boolean' } },
  });
  if (positionals.length !== 1)
    throw new Error(
      'Usage: npm run icons:import -- bundle.json [--write] [--strict]',
    );
  const directory = resolve(
    dirname(fileURLToPath(import.meta.url)),
    '../definitions/icons',
  );
  const report = importBundle(
    JSON.parse(readFileSync(resolve(positionals[0]), 'utf8')),
    directory,
    values.write,
    values.strict,
  );
  console.log(JSON.stringify(report, null, 2));
  if (report.status === 'blocked') process.exitCode = 1;
} catch (error) {
  console.log(
    JSON.stringify(
      {
        status: 'error',
        diagnostics: [
          {
            severity: 'error',
            code: 'IMPORT_FAILED',
            message: error instanceof Error ? error.message : String(error),
          },
        ],
      },
      null,
      2,
    ),
  );
  process.exitCode = 1;
}
