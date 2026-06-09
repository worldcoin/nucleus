import NucleusColors
import NucleusFonts

/// A Nucleus button style token.
///
/// Carries the colors, geometry, and label font for a button variant × size. Colors are
/// theme-aware `NucleusColor`s. `pressedInset` is the point inset the client applies for the
/// pressed state (the pressed visual is a 1pt inset, not a separate color). The full set of
/// button tokens is exposed as `static let`s — for example `NucleusButton.primary48`.
///
/// Nucleus only describes the token; assembling the actual button view is up to the consumer.
public struct NucleusButton: Sendable {
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
