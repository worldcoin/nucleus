import { cpSync, existsSync, mkdirSync, readFileSync, writeFileSync } from 'fs';
import { resolve, dirname } from 'path';

import { BUILD_VERSION, ROOT } from './shared.js';

interface TemplateCopy {
  from: string;
  to: string;
}

const copies: TemplateCopy[] = [
  { from: 'tokens/templates/web/package.json', to: 'build/web/package.json' },
  { from: 'tokens/templates/web/index.d.ts', to: 'build/web/index.d.ts' },
];

export function copyWebPackageTemplates(): void {
  for (const { from, to } of copies) {
    const src = resolve(ROOT, from);
    const dest = resolve(ROOT, to);
    if (!existsSync(src)) {
      console.warn(`  ⚠ template not found: ${from}`);
      continue;
    }
    mkdirSync(dirname(dest), { recursive: true });
    cpSync(src, dest);
  }

  const webPackagePath = resolve(ROOT, 'build/web/package.json');
  if (existsSync(webPackagePath)) {
    const webPackage = JSON.parse(readFileSync(webPackagePath, 'utf8')) as Record<string, unknown>;
    webPackage.version = BUILD_VERSION;
    writeFileSync(webPackagePath, `${JSON.stringify(webPackage, null, 2)}\n`);
  }

  console.log('✓ Templates copied');
}
