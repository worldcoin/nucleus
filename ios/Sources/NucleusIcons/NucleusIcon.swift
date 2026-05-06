import SwiftUI
import UIKit

/// A nucleus design-token icon.
///
/// `NucleusIcon` enumerates every icon shipped by Nucleus. Each case's raw value is the kebab-case icon name (for example `arrow-down-left`). Use ``image(_:)`` or ``uiImage(_:)`` to obtain a SwiftUI `Image` or a `UIImage`. Icons ship in three variants: outline, regular, and solid. Most icons are available in all three; see ``availableVariants`` for the variants supported by a given case.
public extension NucleusIcon {
    /// Stylistic variant of an icon.
    enum Variant: String, CaseIterable, Sendable {
        case outline
        case regular
        case solid
    }

    /// Returns a SwiftUI `Image` for this icon in the requested variant.
    ///
    /// - Parameter variant: The variant to load. Defaults to ``Variant/regular``.
    /// - Returns: A SwiftUI `Image`. The image is rendered as a template by default and responds to `.foregroundColor` / `.foregroundStyle`.
    func image(_ variant: Variant = .regular) -> Image? {
        guard let name = assetName(for: variant) else { return nil }
        return Image(name, bundle: .module)
            .renderingMode(.template)
    }

    /// Returns a `UIImage` for this icon in the requested variant, or `nil` if the asset is missing.
    ///
    /// - Parameter variant: The variant to load. Defaults to ``Variant/regular``.
    /// - Returns: A template-rendered `UIImage` that respects the view's `tintColor`, or `nil` if the asset cannot be located in the bundle.
    func uiImage(_ variant: Variant = .regular) -> UIImage? {
        guard let name = assetName(for: variant) else { return nil }
        return UIImage(named: name, in: .module, with: nil)?
            .withRenderingMode(.alwaysTemplate)
    }

    private func assetName(for variant: Variant) -> String? {
        guard availableVariants.contains(variant) else { return nil }
        return "\(rawValue)-\(variant.rawValue)"
    }
}
