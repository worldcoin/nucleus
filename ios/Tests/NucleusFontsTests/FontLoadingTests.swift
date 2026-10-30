import CoreText
import NucleusFonts
import UIKit
import XCTest

final class FontLoadingTests: XCTestCase {
    @MainActor
    func testBundledFontRegistersAndLoadsWithoutFallback() throws {
        NucleusFont.registerFonts()
        let font = try XCTUnwrap(UIFont(name: NucleusFont.b1.fontName, size: 17))
        XCTAssertEqual(font.fontName, NucleusFont.b1.fontName)

        // Calling registration twice must leave the font usable.
        NucleusFont.registerFonts()
        XCTAssertNotNil(UIFont(name: NucleusFont.b1.fontName, size: 17))
    }

    @MainActor
    func testVariableWeightsAndPrivateUseGlyph() throws {
        NucleusFont.registerFonts()
        for weight: NucleusFont.Weight in [300, 500, 800] {
            let font = NucleusFont.b1.weight(weight).asUIFont()
            XCTAssertEqual(font.familyName, "World Pro MVP")
            let variations = try XCTUnwrap(CTFontCopyVariation(font) as? [NSNumber: NSNumber])
            let axes = try XCTUnwrap(CTFontCopyVariationAxes(font) as? [[String: Any]])
            let weightAxis = try XCTUnwrap(axes.first {
                ($0[kCTFontVariationAxisIdentifierKey as String] as? NSNumber)?.intValue == 0x77676874
            })
            let defaultWeight = try XCTUnwrap(weightAxis[kCTFontVariationAxisDefaultValueKey as String] as? NSNumber)
            // CoreText can omit axes at their default value (300 for this font).
            let actualWeight = variations[NSNumber(value: 0x77676874)] ?? defaultWeight
            XCTAssertEqual(actualWeight.doubleValue, Double(weight.value), accuracy: 0.01)

            // U+E000 is the add-calendar icon in the supplied codepoint map.
            var character: UniChar = 0xE000
            var glyph: CGGlyph = 0
            XCTAssertTrue(CTFontGetGlyphsForCharacters(font, &character, &glyph, 1))
            XCTAssertNotEqual(glyph, 0)
        }
    }
}
