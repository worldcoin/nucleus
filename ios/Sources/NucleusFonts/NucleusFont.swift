import Foundation
import UIKit
import SwiftUI

/// A nucleus design-token font.
///
/// `NucleusFont` is the building block for every typography token in Nucleus. The full set of font tokens is exposed as `static let`s on this type - for example `NucleusFont.WorldPro.b1`. Downstream apps can extend it with their own tokens the same way.
///
/// Nucleus only describes the token and how the font is actually rendered is up to the consumer. Each token carries the values needed to construct a `UIFont` or SwiftUI `Font`: a font name, a size, a numeric weight, a letter-spacing multiplier, a line-height multiplier, and the Dynamic Type style that the token should scale against.
public struct NucleusFont: Equatable, Hashable, Sendable {
    public let fontName: String
    public let size: Double
    public let weight: Weight
    public let letterSpacing: Double
    public let lineHeight: LineHeight
    public let usesMonospacedDigits: Bool
    public let dynamicTypeStyle: UIFont.TextStyle

    /// Creates a new font descriptor for the variable WorldPro font.
    ///
    /// - Parameters:
    ///     - fontName: The PostScript name of the underlying font face.
    ///     - size: The size of the font.
    ///     - weight: The variable weight of the font. This can either be an integer between 300...800 or a predetermined value like `.regular` or `.bold`.
    ///     - letterSpacing: The letter-spacing multiplier. Use negative values for tighter spacing and positive values for looser spacing. This value is multiplied by the font size, so a letter spacing of `-0.02` with a font size of `56` results in a kerning value of `-1.12` points.
    ///     - lineHeight: The line-height multiplier. A value of `1.2` results in a line height 20% larger than the font's default line height.
    ///     - monospacedDigits: Whether numeric glyphs should render with monospaced advances.
    ///     - dynamicTypeStyle: The style that will be used when scaling the font for dynamic type. Use the style that most closely matches your font size. See [here](https://developer.apple.com/design/human-interface-guidelines/typography#iOS-iPadOS-Dynamic-Type-sizes) for more info on the sizing of each style.
    public init(
        fontName: String,
        size: Double,
        weight: Weight = .regular,
        letterSpacing: Double = 0,
        lineHeight: LineHeight = .regular,
        monospacedDigits: Bool = false,
        dynamicTypeStyle: UIFont.TextStyle = .body
    ) {
        self.fontName = fontName
        self.size = size
        self.weight = weight
        self.letterSpacing = letterSpacing
        self.lineHeight = lineHeight
        self.usesMonospacedDigits = monospacedDigits
        self.dynamicTypeStyle = dynamicTypeStyle
    }
}

// MARK: - Mutating Methods

extension NucleusFont {
    /// Mutate an existing NucleusFont with a specified variable weight. This can either be an integer between 300...800 or a predetermined value like `.regular` or `.bold`.
    public func weight(_ weight: Weight) -> NucleusFont {
        return NucleusFont(fontName: fontName, size: size, weight: weight, letterSpacing: letterSpacing, lineHeight: lineHeight, monospacedDigits: usesMonospacedDigits, dynamicTypeStyle: dynamicTypeStyle)
    }

    /// Mutate the letter-spacing multiplier of an existing NucleusFont. Use negative values for tighter spacing and positive values for looser spacing. This value is multiplied by the font size, so a letter spacing of `-0.02` with a font size of `56` results in a kerning value of `-1.12` points.
    public func letterSpacing(_ letterSpacing: Double) -> NucleusFont {
        return NucleusFont(fontName: fontName, size: size, weight: weight, letterSpacing: letterSpacing, lineHeight: lineHeight, monospacedDigits: usesMonospacedDigits, dynamicTypeStyle: dynamicTypeStyle)
    }

    /// Mutate the line-height multiplier of an existing NucleusFont. A value of `1.2` results in a line height 20% larger than the font's default line height.
    public func lineHeight(_ lineHeight: LineHeight) -> NucleusFont {
        return NucleusFont(fontName: fontName, size: size, weight: weight, letterSpacing: letterSpacing, lineHeight: lineHeight, monospacedDigits: usesMonospacedDigits, dynamicTypeStyle: dynamicTypeStyle)
    }

    /// Make the numbers in the font monospaced.
    public func monospacedDigits() -> NucleusFont {
        return NucleusFont(fontName: fontName, size: size, weight: weight, letterSpacing: letterSpacing, lineHeight: lineHeight, monospacedDigits: true, dynamicTypeStyle: dynamicTypeStyle)
    }
}

// MARK: - Font Construction

extension NucleusFont {
    public func asUIFont(compatibleWith traitCollection: UITraitCollection? = nil) -> UIFont {
        var descriptor: UIFontDescriptor

        if let font = UIFont(name: fontName, size: size) {
            descriptor = font.fontDescriptor

            // apply the variable `wght` axis.
            let variationAttributes: [NSNumber: Any] = [
                NSNumber(value: Self.weightAxisTag): Double(weight.value)
            ]

            // Create the descriptor and apply the attributes
            descriptor = descriptor.addingAttributes([
                kCTFontVariationAttribute as UIFontDescriptor.AttributeName: variationAttributes
            ])
        } else {
            descriptor = UIFont.systemFont(ofSize: size).fontDescriptor
        }

        // Apply monospacing if needed
        var featureSettings: [[UIFontDescriptor.FeatureKey: Int]] = []

        if usesMonospacedDigits {
            featureSettings.append([
                UIFontDescriptor.FeatureKey.type: kNumberSpacingType,
                UIFontDescriptor.FeatureKey.selector: kMonospacedNumbersSelector,
            ])
        }

        if !featureSettings.isEmpty {
            descriptor = descriptor.addingAttributes([.featureSettings: featureSettings])
        }

        // Create dynamically scaled font
        let font = UIFont(descriptor: descriptor, size: size)
        let metrics = UIFontMetrics(forTextStyle: dynamicTypeStyle)
        return metrics.scaledFont(for: font, compatibleWith: traitCollection)
    }

    public func asFont() -> Font {
        return Font(asUIFont())
    }

    private static let weightAxisTag: Int = {
        let bytes = Array("wght".utf8)
        return (Int(bytes[0]) << 24) | (Int(bytes[1]) << 16) | (Int(bytes[2]) << 8) | Int(bytes[3])
    }()
}

// MARK: - Font Loading

@MainActor
extension NucleusFont {
    private static var isRegistered = false

    public static func registerFonts() {
        guard !isRegistered else { return }
        guard let fonts = Bundle.module.urls(forResourcesWithExtension: "ttf", subdirectory: nil) else {
            return
        }
        for fontURL in fonts {
            CTFontManagerRegisterFontsForURL(fontURL as CFURL, .process, nil)
        }
        isRegistered = true
    }
}
