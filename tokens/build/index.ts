import { buildColors } from './colors.js';
import { buildFonts } from './fonts.js';
import { buildIcons } from './icons.js';
import { copyWebPackageTemplates } from './web-package.js';

async function main(): Promise<void> {
  console.log('Building Nucleus tokens…');

  await buildColors();
  await buildFonts();
  buildIcons();

  console.log('\n✓ Tokens built');
  copyWebPackageTemplates();
  console.log('\nDone!');
}

main().catch((err) => {
  console.error(err);
  process.exit(1);
});
