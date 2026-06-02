import { buildButtons } from './buttons.js';
import { buildColors } from './colors.js';
import { buildFonts } from './fonts.js';
import { buildIcons } from './icons.js';
import { buildWebTypes } from './types.js';
import { copyWebPackageTemplates } from './web-package.js';

function main(): void {
  console.log('Building Nucleus tokens…');

  buildColors();
  buildFonts();
  buildButtons();
  buildIcons();
  buildWebTypes();

  console.log('\n✓ Tokens built');
  copyWebPackageTemplates();
  console.log('\nDone!');
}

try {
  main();
} catch (err) {
  console.error(err);
  process.exit(1);
}
