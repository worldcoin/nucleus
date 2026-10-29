# Import icons from Figma

The scripts sync the [UI Kit 5.0 Iconography section](https://www.figma.com/design/w4tJNJK5PKXQAksXgfnKsM/UI-Kit-5.0?node-id=1181-11691)
into `tokens/definitions/icons/`, then the existing build generates platform assets.

## Download

Use Node.js 22 or newer and `npm ci`. Supply `FIGMA_ACCESS_TOKEN` through the environment or your
secret manager. It needs `file_content:read` and access to this file. The scripts do not
source `.env` files. A connected Figma plugin session does not supply this REST credential.

```sh
npm run icons:download -- --out /tmp/nucleus-icons.json
# Replay a source revision (the version is printed by download and stored in the bundle):
npm run icons:download -- --version VERSION --out /tmp/nucleus-icons-replay.json
```

To load a local `.env` explicitly:

```sh
node --env-file=.env --import tsx tokens/import/download-icons.ts --out /tmp/nucleus-icons.json
```

The downloader requests the section's document and version together, then pins every SVG
render to that version. Source settings are fixed in `tokens/import/icon-bundle.ts`:
file `w4tJNJK5PKXQAksXgfnKsM`, section `1181:11691`.

It exports the explicit `Weight=Regular` and `Weight=Solid` components from named component
sets, skipping Dynamic, placeholder, and loose frames. Both explicit variants are required
for each icon. No total count is hardcoded. The initial October 29, 2026 export had 212 sets
and 424 SVGs; 97 Solid exports matched the Regular artwork. They remain separate variants.

Rendering requests are sequential batches of 40 nodes; SVG downloads use four workers.
Adjust with `--batch-size 1..100` and `--concurrency 1..8`. Requests time out after 30 seconds.
Network errors, HTTP 429, and HTTP 5xx get at most four attempts. Retry-After is honored up
to 30 seconds; longer limits stop the export so it can be retried later. Permanent errors,
missing renders, and unsupported artwork fail the operation. The output bundle is replaced
only after all downloads and validation succeed. Token headers go only to the Figma API,
not to the returned SVG asset URLs.

## Review and import

```sh
npm run icons:import -- /tmp/nucleus-icons.json
npm run icons:import -- /tmp/nucleus-icons.json --write
npm run build
npm test
npm run typecheck
npm run lint
# Must report no added/changed icons and manifestChanged: false:
npm run icons:import -- /tmp/nucleus-icons.json
```

Import defaults to a dry run and prints sorted lists of additions, changes, unchanged icons,
and retained public names. It validates the entire bundle before writing. It checks names,
IDs, completeness against the inventory, generated symbol collisions, SHA-256 checksums,
24×24 SVG bounds, and the existing Android SVG converter's supported artwork.
Each changed file is replaced with a temporary-file rename. A multi-file import is not a
filesystem transaction: if filesystem writes fail, rerun the same bundle to converge.
Do not run concurrent imports into the same checkout.

Names missing from the export are retained, never deleted. For example, Figma now uses
`person-lock` while this repo already exposes `personal-vault`; both remain available.
Commit source SVGs, the generated manifest, and regenerated iOS/Android assets together.
The web build is ignored by Git. Native runtime rendering still needs platform testing.

## Reproducibility and provenance

A bundle is self-contained JSON with these fields:

- `schemaVersion: 1`, `fileKey`, `sectionId`, and the captured Figma `version`.
- `inventory`: every expected `{name, variant, nodeId}` from that source section.
- `icons`: the same identities plus `svg` and `sha256` for every render.

SVG normalization only converts line endings to LF, trims surrounding whitespace, and adds
one trailing newline. Identity arrays are sorted by variant/name with a locale-independent
comparison. Serialization uses fixed field ordering and two-space JSON indentation.
No timestamps, authentication tokens, or expiring download URLs enter the bundle or manifest.
Changing request batch size, concurrency, or input array order does not change canonical bytes.

`tokens/definitions/icons/figma-manifest.json` records the Figma revision, each source node and
SVG checksum, and the SHA-256 of the canonical bundle. A saved bundle is the exact replay
artifact: preserve it in durable storage alongside the change. The current snapshot is
`tokens/import/snapshots/2415886286182418274.json`. Re-downloading a pinned Figma
revision fixes the source design, but Figma's hosted SVG renderer may change over time;
exact byte reproduction is guaranteed by replaying the saved bundle, not by the remote renderer.
The hashes detect corruption; they are not signatures authenticating Figma's authorship.

The scripts use the official [Figma file and image endpoints](https://developers.figma.com/docs/rest-api/file-endpoints/).
Downloader tests mock those endpoints to check pinning, batching, retries, and authentication
boundaries. Import tests use temporary directories to check determinism, idempotency, and
rejection of incomplete/corrupted bundles before writes.
