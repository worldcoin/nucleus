import SwiftUI
import UIKit

/// A nucleus design-token icon.
///
/// `NucleusIcon` enumerates every icon shipped by Nucleus. Each case's raw value is the kebab-case icon name (for example `arrow-down-left`). Icons ship in three variants: outline, regular, and solid. Most icons are available in all three; see ``availableVariants`` for the variants supported by a given case.
public extension NucleusIcon {
    /// Stylistic variant of an icon.
    enum Variant: String, CaseIterable, Sendable {
        case outline
        case regular
        case solid
    }

    static let bundle = Bundle.module

    func assetName(for variant: Variant) -> String? {
        guard availableVariants.contains(variant) else { return nil }
        return "\(rawValue)-\(variant.rawValue)"
    }
}
