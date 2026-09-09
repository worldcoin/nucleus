# b1 font comparison on iOS

Open `FontComparison.xcodeproj`, choose the `FontComparison` scheme, and run on an iPad or iPhone simulator. The checked-in project references `NucleusFonts` from the repository root. Xcode 26 or newer is required by that package.

The app compares the font from `main` before the font update (`WorldProMVPLH-Regular`) with the current `NucleusFont.b1` font. The previous TTF is bundled only in this demo. Both font names are checked at launch so a missing font cannot silently fall back to the system font.

Both samples use the same four paragraphs and the b1 token: 17 pt, variable weight 350, −0.5% tracking (−0.085 pt at the standard size), and a 1.3 line-height multiplier. The previous sample copies the current b1 settings and changes only the font name. Spacing matches the package's `PreviewFontModifier`: `UIFont.lineHeight * 0.3` is added between lines, with half that amount as vertical padding on each paragraph. Separate paragraphs have an additional, identical 16 pt gap.

The token values match the live UI Kit 5.0 text styles. This demo preserves the existing iOS preview rendering behavior to isolate the font change. That behavior bases spacing on native font metrics; Figma bases its percentage on font size, so this is not a pixel-parity reference for Figma. See [typography alignment](../../../docs/typography.md).

iPad shows equal-width columns; iPhone stacks them. Use Previous and Updated to compare one font at a time at full width. Text follows the device's Dynamic Type setting. Each sample reports its native UIFont line height, added line spacing, and measured paragraph-block height.

## Observed result

On an iPad Pro 11-inch (M5), iOS 26.5, portrait, at the standard text size, using the Figma-aligned b1 token:

| Measurement | Previous | Updated |
| --- | ---: | ---: |
| UIFont line height | 17.00 pt | 18.89 pt |
| Added line spacing | 5.10 pt | 5.67 pt |
| Four-paragraph block | 534.4 pt | 589.7 pt |

The updated block is 55.3 pt (approximately 10.3%) taller in this layout. These measurements describe this SwiftUI rendering path; other consumers can apply the line-height token differently.

To regenerate the Xcode project after changing `project.yml`, run `xcodegen generate` in this directory. Code signing is disabled for simulator testing; configure signing to run on a physical device.
