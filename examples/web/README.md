# Web Example

Web sample app for Nucleus with two token source modes:

- `local` reads generated files directly from `build/web`
- `package` reads the local npm package artifact from `build/web`

## Setup

```bash
npm ci
npm run build
cd examples/web
npm install
```

Run the root install before building, then run `npm install` in `examples/web` after `npm run build` so the local package artifact in `build/web` can resolve for package mode.

## Development

```bash
npm run dev
```

Open [http://localhost:3000](http://localhost:3000).

Use package mode with:

```bash
npm run dev:package
```

Build package mode with:

```bash
npm run build:package
```
