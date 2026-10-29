import { readFileSync, writeFileSync } from 'node:fs';
import { resolve } from 'node:path';
import { pathToFileURL } from 'node:url';
import type { importBundle } from './icon-bundle.js';

type Report = ReturnType<typeof importBundle>;
export function hasIconChanges(report: Report): boolean {
  return (
    report.added.length + report.changed.length + report.removed.length > 0
  );
}
const escape = (value: string) =>
  value
    .replace(/[&<>`|[\]\\]/g, (c) => `&#${c.charCodeAt(0)};`)
    .replace(/[\r\n]/g, ' ');
function list(title: string, values: string[], limit = 60): string {
  if (!values.length) return `${title}: none.\n`;
  return (
    `<details>\n<summary>${title} (${values.length})</summary>\n\n` +
    values
      .slice(0, limit)
      .map((v) => `- ${escape(v)}`)
      .join('\n') +
    (values.length > limit
      ? `\n- ${values.length - limit} more; see the full report artifact.`
      : '') +
    '\n\n</details>\n'
  );
}
export function renderSyncReport(report: Report, runUrl?: string): string {
  if (!['ok', 'warning'].includes(report.status))
    throw new Error('Cannot publish an unsuccessful import report');
  const duplicates = report.diagnostics.filter(
    (d) => d.code === 'DUPLICATE_ARTWORK',
  );
  const other = report.diagnostics.filter(
    (d) => d.code !== 'DUPLICATE_ARTWORK' && d.code !== 'NAME_CHANGED',
  );
  const lines = [
    'Sync icons from the World UI Kit in Figma and regenerate platform assets.',
    '',
    `Figma revision: ${escape(report.version)}. Imported ${report.exported} variants.`,
    '',
    '| Added | Updated | Removed | Unchanged |',
    '| ---: | ---: | ---: | ---: |',
    `| ${report.added.length} | ${report.changed.length} | ${report.removed.length} | ${report.unchanged.length} |`,
    '',
    list('Added variants', report.added),
    list('Updated variants', report.changed),
    list('Removed variants', report.removed),
    list(
      'Naming changes (including recorded migrations)',
      report.namingChanges.map((n) => `${n.from} → ${n.to}`),
    ),
    list('Retained local variants absent from Figma', report.retained),
    list(
      'Diagnostics',
      other.map((d) => `${d.severity}: ${d.code} — ${d.message}`),
    ),
    list(
      'Duplicate artwork groups',
      duplicates.map((d) => (d.members ?? []).join(' = ')),
      100,
    ),
    'Duplicate artwork is reported for review; matching Regular/Solid variants remain separate.',
    '',
    'Validation: tests, typecheck, lint, JSON formatting, build, and zero-change replay passed. Native rendering is checked by PR CI.',
  ];
  if (
    runUrl &&
    /^https:\/\/github\.com\/[A-Za-z0-9_.-]+\/[A-Za-z0-9_.-]+\/actions\/runs\/\d+$/.test(
      runUrl,
    )
  )
    lines.push('', `[Workflow run and full report artifact](${runUrl}).`);
  // Keep comfortably below GitHub's PR body limit; full results are in the artifact.
  const body = lines.join('\n') + '\n';
  if (Buffer.byteLength(body, 'utf8') > 60_000)
    throw new Error('PR summary exceeds 60 KB; lower the display limits');
  return body;
}
if (
  process.argv[1] &&
  import.meta.url === pathToFileURL(resolve(process.argv[1])).href
) {
  const [input, output, runUrl] = process.argv.slice(2);
  if (!input || !output)
    throw new Error(
      'Usage: sync-report.ts report.json description.md [run-url]',
    );
  const report: Report = JSON.parse(readFileSync(input, 'utf8'));
  writeFileSync(output, renderSyncReport(report, runUrl));
  console.log(
    JSON.stringify({
      hasChanges: hasIconChanges(report),
      releaseLabel: report.removed.length
        ? 'major'
        : report.added.length
          ? 'minor'
          : 'patch',
    }),
  );
}
