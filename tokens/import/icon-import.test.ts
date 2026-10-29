import assert from 'node:assert/strict';
import test from 'node:test';
import {
  mkdtempSync,
  readFileSync,
  readdirSync,
  rmSync,
  writeFileSync,
  mkdirSync,
  statSync,
} from 'node:fs';
import { tmpdir } from 'node:os';
import { join } from 'node:path';
import {
  FILE_KEY,
  SECTION_ID,
  normalizeSvg,
  sha256,
  serializeBundle,
  validateBundle,
  importBundle,
  type IconBundle,
} from './icon-bundle.js';
import { downloadBundle } from './download-icons.js';

const svg =
  '<svg width="24" height="24" viewBox="0 0 24 24"><path d="M2 2H22V22H2Z" fill="#1F1F1F"/></svg>\n';
function bundle(): IconBundle {
  const inventory = (['regular', 'solid'] as const).map((variant, i) => ({
    name: 'test-icon',
    variant,
    nodeId: `1:${i + 1}`,
  }));
  return {
    schemaVersion: 1,
    fileKey: FILE_KEY,
    sectionId: SECTION_ID,
    version: '12345',
    inventory,
    icons: inventory.map((i) => ({ ...i, svg, sha256: sha256(svg) })),
  };
}
const temp = () => mkdtempSync(join(tmpdir(), 'nucleus-icons-test-'));

test('bundle serialization is independent of input order and line endings', () => {
  const a = bundle(),
    b = bundle();
  b.inventory.reverse();
  b.icons.reverse();
  b.icons.forEach((i) => {
    i.svg = i.svg.replace(/\n/g, '\r\n');
  });
  assert.equal(serializeBundle(a), serializeBundle(b));
  assert.equal(normalizeSvg('  <svg/>\r\n'), '<svg/>\n');
});

test('invalid/incomplete bundles never write any source files', () => {
  const dir = temp();
  try {
    const cases: Array<(b: IconBundle) => void> = [
      (b) => {
        b.icons.pop();
      },
      (b) => {
        b.icons.push(b.icons[0]);
      },
      (b) => {
        b.icons[1].svg += '<script/>';
      },
      (b) => {
        b.inventory[0].name = '../escape';
      },
      (b) => {
        b.icons[0].nodeId = '99:99';
      },
      (b) => {
        b.inventory.pop();
      },
      (b) => {
        b.icons[1].svg = svg.replace('24 24', '32 32');
        b.icons[1].sha256 = sha256(b.icons[1].svg);
      },
      (b) => {
        b.icons[1].svg = svg.replace('<path', '<image');
        b.icons[1].sha256 = sha256(b.icons[1].svg);
      },
      (b) => {
        b.version = '';
      },
    ];
    for (const mutate of cases) {
      const input = bundle();
      mutate(input);
      assert.throws(() => importBundle(input, dir, true));
      assert.deepEqual(readdirSync(dir), []);
    }
  } finally {
    rmSync(dir, { recursive: true, force: true });
  }
});

test('imports preserve legacy names, dry-run is read-only, second import does no writes', () => {
  const dir = temp();
  try {
    mkdirSync(join(dir, 'regular'));
    writeFileSync(join(dir, 'regular/legacy.svg'), svg);
    assert.equal(importBundle(bundle(), dir).added.length, 2);
    assert.deepEqual(readdirSync(dir), ['regular']);
    const first = importBundle(bundle(), dir, true);
    assert.deepEqual(first.retained, ['regular/legacy']);
    const path = join(dir, 'regular/test-icon.svg'),
      manifest = join(dir, 'figma-manifest.json');
    const before = [
      statSync(path).mtimeMs,
      statSync(manifest).mtimeMs,
      readFileSync(manifest, 'utf8'),
    ];
    const second = importBundle(bundle(), dir, true);
    assert.deepEqual(second.changed, []);
    assert.deepEqual(second.added, []);
    assert.equal(second.manifestChanged, false);
    assert.deepEqual(
      [
        statSync(path).mtimeMs,
        statSync(manifest).mtimeMs,
        readFileSync(manifest, 'utf8'),
      ],
      before,
    );
  } finally {
    rmSync(dir, { recursive: true, force: true });
  }
});

function document(count: number) {
  return {
    id: '0:0',
    name: 'document',
    type: 'DOCUMENT',
    children: [
      {
        id: SECTION_ID,
        name: 'Iconography',
        type: 'SECTION',
        children: Array.from({ length: count }, (_, i) => ({
          id: `2:${i}`,
          name: `icon-${i}`,
          type: 'COMPONENT_SET',
          children: ['Regular', 'Solid', 'Dynamic'].map((weight, j) => ({
            id: `3:${i * 3 + j}`,
            name: `Weight=${weight}`,
            type: 'COMPONENT',
          })),
        })).reverse(),
      },
    ],
  };
}
function transport(count: number, failure?: 'missing' | 'rate' | 'auth') {
  const calls: Array<{ url: URL; headers: Headers }> = [];
  let active = 0,
    maximum = 0,
    apiTries = 0;
  const mock = (async (input: string | URL | Request, init?: RequestInit) => {
    const url = new URL(String(input));
    calls.push({ url, headers: new Headers(init?.headers) });
    if (url.hostname === 'api.figma.com') {
      if (failure === 'auth') return new Response('', { status: 403 });
      if (failure === 'rate' && apiTries++ === 0)
        return new Response('', {
          status: 429,
          headers: { 'retry-after': '0' },
        });
      if (url.pathname.includes('/files/'))
        return Response.json({ version: '12345', document: document(count) });
      const ids = url.searchParams.get('ids')!.split(',');
      return Response.json({
        images: Object.fromEntries(
          ids.map((id) => [
            id,
            failure === 'missing' ? null : `https://assets.example/${id}`,
          ]),
        ),
      });
    }
    active++;
    maximum = Math.max(maximum, active);
    await new Promise((r) => setTimeout(r, 2));
    active--;
    return new Response(svg);
  }) as typeof fetch;
  return { mock, calls, maximum: () => maximum };
}

test('version-pinned export batches requests, bounds downloads, strips auth from asset requests', async () => {
  const t = transport(7);
  const output = await downloadBundle('test-secret', '12345', {
    fetch: t.mock,
    batchSize: 4,
    concurrency: 2,
  });
  assert.equal(validateBundle(output).icons.length, 14);
  assert.ok(t.maximum() <= 2);
  assert.equal(
    t.calls.filter((c) => c.url.pathname.includes('/images/')).length,
    4,
  );
  for (const call of t.calls) {
    if (call.url.hostname === 'api.figma.com') {
      assert.equal(call.url.searchParams.get('version'), '12345');
      assert.equal(call.headers.get('X-Figma-Token'), 'test-secret');
    } else assert.equal(call.headers.has('X-Figma-Token'), false);
  }
  const another = transport(7);
  assert.equal(
    serializeBundle(output),
    serializeBundle(
      await downloadBundle('test-secret', '12345', {
        fetch: another.mock,
        batchSize: 3,
        concurrency: 1,
      }),
    ),
  );
});

test('latest export captures the returned version for every render', async () => {
  const t = transport(1);
  await downloadBundle('secret', undefined, { fetch: t.mock });
  assert.equal(t.calls[0].url.searchParams.has('version'), false);
  assert.equal(
    t.calls
      .find((c) => c.url.pathname.includes('/images/'))!
      .url.searchParams.get('version'),
    '12345',
  );
});

test('rate limiting retries, missing renders fail, auth errors never retry', async () => {
  const rate = transport(1, 'rate'),
    delays: number[] = [];
  await downloadBundle('secret', '12345', {
    fetch: rate.mock,
    sleep: async (ms) => {
      delays.push(ms);
    },
  });
  assert.deepEqual(delays, [0]);
  await assert.rejects(
    downloadBundle('secret', '12345', { fetch: transport(1, 'missing').mock }),
    /No SVG render/,
  );
  const auth = transport(1, 'auth');
  await assert.rejects(
    downloadBundle('secret', '12345', { fetch: auth.mock }),
    /HTTP 403/,
  );
  assert.equal(auth.calls.length, 1);
});

test('source versions cannot drift and repeated transient failures stop', async () => {
  await assert.rejects(
    downloadBundle('secret', 'different-version', { fetch: transport(1).mock }),
    /unexpected file version/,
  );
  let tries = 0;
  const unavailable = (async () => {
    tries++;
    return new Response('', { status: 503 });
  }) as typeof fetch;
  await assert.rejects(
    downloadBundle('secret', '12345', {
      fetch: unavailable,
      sleep: async () => {},
    }),
    /after 4 attempts/,
  );
  assert.equal(tries, 4);
  const limited = (async () =>
    new Response('', {
      status: 429,
      headers: { 'retry-after': '120' },
    })) as typeof fetch;
  await assert.rejects(
    downloadBundle('secret', '12345', {
      fetch: limited,
      sleep: async () => {
        assert.fail('must not retry early');
      },
    }),
    /later retry/,
  );
});

test('generated symbol collisions with retained public names fail before writes', () => {
  const dir = temp();
  try {
    mkdirSync(join(dir, 'regular'));
    writeFileSync(join(dir, 'regular/icon1.svg'), svg);
    const input = bundle();
    for (const i of [...input.inventory, ...input.icons]) i.name = 'icon-1';
    assert.throws(
      () => importBundle(input, dir, true),
      /collision with existing icon/,
    );
    assert.deepEqual(readdirSync(join(dir, 'regular')), ['icon1.svg']);
  } finally {
    rmSync(dir, { recursive: true, force: true });
  }
});
