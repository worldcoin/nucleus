# Typography alignment with UI Kit 5.0

Verified on 2026-09-09 against the live local text styles and styled specimens in [UI Kit 5.0 → Typography](https://www.figma.com/design/w4tJNJK5PKXQAksXgfnKsM/UI-Kit-5.0?node-id=1297-41). Use the style properties, including the numeric `wght` variation, as the source of truth: some handwritten specimen labels have outdated weights.

All styles use **World Pro MVP**. Nucleus resolves the supplied font through its PostScript name, `WorldProMVP-Regular`. Sizes below are Figma pixels, mapped to CSS px, iOS base points, and Android sp. Tracking is a percentage of font size; line height is also a percentage of font size.

| Figma style | Token | Size | Weight | Tracking | Line height |
| --- | --- | ---: | ---: | ---: | ---: |
| Display/D1 | d1 | 56 | 550 | −2% | 100% |
| Headline/H1 | h1 | 30 | 450 | −1.5% | 110% |
| Headline/H2 | h2 | 27 | 450 | −1.5% | 110% |
| Headline/H3 | h3 | 24 | 450 | −1.5% | 110% |
| Headline/H4 | h4 | 21 | 450 | −1% | 110% |
| Headline/H5 | h5 | 19 | 450 | −1% | 110% |
| Subtitle/S1 | s1 | 17 | 450 | −1% | 120% |
| Subtitle/S2 | s2 | 15 | 450 | 0% | 120% |
| Subtitle/S3 | s3 | 13 | 450 | 0% | 120% |
| Label/L1 | l1 | 17 | 500 | −1% | 120% |
| Label/L2 | l2 | 15 | 500 | −1% | 120% |
| Label/L3 | l3 | 13 | 500 | −1% | 120% |
| Body/B1 | b1 | 17 | 350 | −0.5% | 130% |
| Body/B2 | b2 | 15 | 350 | −0.5% | 130% |
| Body/B3 | b3 | 13 | 350 | 0% | 130% |
| Caption/C1 | c1 | 11 | 350 | +0.3% | 140% |

The styles also specify zero paragraph spacing and indentation, original case, no decoration, and no leading trim. Nucleus does not encode these fields; consumers should preserve those defaults. Additional spacing between separate text elements belongs to the containing layout. Dynamic Type mappings are platform behavior, not Figma text-style properties.

## Changes from v0.2.9

- h1–h5 weight: 500 → 450; line height: 120% → 110%.
- l1–l3 weight: 550 → 500.
- b1 and b2 tracking: 0 → −0.5% (`-0.005` in the source tokens).
- b3 line height: 120% → 130% (`1.3` in the source tokens).
- c1 tracking: 0 → +0.3% (`0.003` in the source tokens).

The other four styles already matched. The latest Figma headline revision sets h1–h5 line height to 110%. Font sizes are unchanged. These updates intentionally change appearance and can affect wrapping and fixed-height layouts; b3 gains 1.3 base units per line.

## iOS rendering caveat

Nucleus provides font descriptors; `asUIFont()` applies the family, weight, and Dynamic Type size. Consumers apply tracking and line height themselves. For Figma-compatible spacing, use the scaled font point size as the basis: b1 at standard size targets `17 × 1.3 = 22.1 pt`, with `17 × -0.005 = -0.085 pt` tracking.

The package's DEBUG-only `PreviewFontModifier` and the font comparison example retain the existing behavior of adding `UIFont.lineHeight × (multiplier - 1)`. This uses native font metrics and does **not** guarantee Figma line-height parity. The new TTF changes those metrics. Matching token values therefore does not establish matching rendered text-block heights in consuming iOS apps. Validate their text renderer and Dynamic Type layouts before release.

Edit `tokens/definitions/font/fonts.json` and run `npm run build` to regenerate the web, iOS, and Android outputs. Do not edit generated defaults directly.
