import Foundation

/// A Nucleus design token icon.
///
/// A `NucleusIcon` pairs a ``Symbol``(eg. `arrow-down-left`) with the stylistic ``Variant`` it should render in. Icons ship in three variants: outline, regular, and solid. Most symbols are available in all three; see ``Symbol/availableVariants`` for the variants supported by a given symbol.
public struct NucleusIcon: Equatable, Hashable, Sendable {
    /// Stylistic variant of an icon.
    public enum Variant: String, CaseIterable, Sendable {
        case outline
        case regular
        case solid
    }

    /// The glyph this icon renders.
    public let symbol: Symbol
    /// The stylistic variant the symbol renders in.
    public let variant: Variant

    public init(_ symbol: Symbol, variant: Variant) {
        self.symbol = symbol
        self.variant = variant
    }

    public static let bundle = Bundle.module

    /// The asset catalog name for this icon, or `nil` if the symbol isn't shipped in this variant.
    public var assetName: String? {
        guard symbol.availableVariants.contains(variant) else { return nil }
        return "\(symbol.rawValue)-\(variant.rawValue)"
    }
}
