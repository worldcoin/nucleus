import NucleusColors
import NucleusFonts
import NucleusTokens

/// A Nucleus button style token.
///
/// Carries the colors, geometry, and label font for a button variant × size. Colors are semantic `NucleusColor`s. . The full set of button tokens are exposed as `static` accessors — for example `NucleusButton.primary48`.
///
/// Nucleus only describes the token. Assembling the actual button view is up to the consumer.
public protocol NucleusButtonToken: Sendable {
    var background: NucleusColor { get }
    var content: NucleusColor { get }
    var border: NucleusColor? { get }
    var height: Double { get }
    var cornerRadius: Double { get }
    var paddingHorizontal: Double { get }
    var paddingVertical: Double { get }
    var font: NucleusFont { get }
    var pressedInset: Double { get }
}

public struct NucleusButton: NucleusButtonToken {
    public let background: NucleusColor
    public let content: NucleusColor
    public let border: NucleusColor?
    public let height: Double
    public let cornerRadius: Double
    public let paddingHorizontal: Double
    public let paddingVertical: Double
    public let font: NucleusFont
    public let pressedInset: Double

    public init(
        background: NucleusColor,
        content: NucleusColor,
        border: NucleusColor? = nil,
        height: Double,
        cornerRadius: Double,
        paddingHorizontal: Double,
        paddingVertical: Double,
        font: NucleusFont,
        pressedInset: Double = 0
    ) {
        self.background = background
        self.content = content
        self.border = border
        self.height = height
        self.cornerRadius = cornerRadius
        self.paddingHorizontal = paddingHorizontal
        self.paddingVertical = paddingVertical
        self.font = font
        self.pressedInset = pressedInset
    }
}
