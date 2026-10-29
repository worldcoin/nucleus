import assert from 'node:assert/strict';
import test from 'node:test';
import { renderSyncReport, hasIconChanges } from './sync-report.js';
import type { importBundle } from './icon-bundle.js';

function report(): ReturnType<typeof importBundle> {
  return {
    status: 'warning',
    mode: 'write',
    applied: true,
    strict: false,
    version: '12345',
    exported: 3,
    added: ['regular/new-icon'],
    changed: ['solid/changed-icon'],
    removed: ['regular/old-icon'],
    unchanged: [],
    retained: ['regular/legacy'],
    manifestChanged: true,
    namingChanges: [{ from: 'old-icon', to: 'new-icon' }],
    diagnostics: [
      {
        severity: 'warning',
        code: 'DUPLICATE_ARTWORK',
        icon: 'new-icon',
        members: ['regular/new-icon', 'solid/new-icon'],
        message: 'Same artwork',
      },
      {
        severity: 'warning',
        code: 'MISSING_VARIANT',
        icon: 'other-icon',
        variant: 'solid',
        message: 'No solid variant',
      },
    ],
  };
}

test('PR description includes changes, naming migrations, duplicates and diagnostics', () => {
  const body = renderSyncReport(
    report(),
    'https://github.com/worldcoin/nucleus/actions/runs/123',
  );
  for (const expected of [
    '12345',
    'regular/new-icon',
    'solid/changed-icon',
    'regular/old-icon',
    'old-icon → new-icon',
    'regular/new-icon = solid/new-icon',
    'MISSING_VARIANT',
    'regular/legacy',
    'https://github.com/worldcoin/nucleus/actions/runs/123',
  ])
    assert.ok(body.includes(expected), expected);
});

test('metadata-only changes do not create icon PRs', () => {
  const input = report();
  input.added = [];
  input.changed = [];
  input.removed = [];
  assert.equal(hasIconChanges(input), false);
  for (const field of ['added', 'changed', 'removed'] as const) {
    input[field] = ['regular/real-change'];
    assert.equal(hasIconChanges(input), true);
    input[field] = [];
  }
});

test('large reports are summarized, untrusted content is escaped, unsuccessful runs cannot become PRs', () => {
  const input = report();
  input.added = Array.from({ length: 1000 }, (_, i) => `regular/icon-${i}`);
  input.diagnostics[1].message = '</details>\n<script>untrusted</script>';
  const body = renderSyncReport(input, 'javascript:alert(1)');
  assert.ok(Buffer.byteLength(body) < 60_000);
  assert.match(body, /940 more/);
  assert.doesNotMatch(body, /<script>|javascript:/);
  input.status = 'blocked';
  assert.throws(() => renderSyncReport(input), /unsuccessful/);
});
