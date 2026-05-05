import Foundation
import UIKit

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
        weight: Weight,
        letterSpacing: Double,
        lineHeight: LineHeight,
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

// MARK: - Helper Types

public extension NucleusFont {
    struct LineHeight: Equatable, Hashable, Sendable, ExpressibleByFloatLiteral {
        public let value: Double

        public init(floatLiteral value: Double) {
            self.value = value
        }
    }

    struct Weight: Equatable, Hashable, Sendable, ExpressibleByIntegerLiteral {
        public let value: Int

        public init(integerLiteral value: IntegerLiteralType) {
            self.value = value
        }
    }
}
