import SwiftUI
import UIKit

/// A nucleus design-token color.
///
/// `NucleusColor` is the building block for every color in nucleus. The full set of nucleus tokens is exposed as `static let`s on this type — primitives (`NucleusColor.grey900`) and semantics (`NucleusColor.surfacePrimary`) — and downstream apps can extend it with their own colors the same way.
///
/// Each value carries a `light` set of color components and an optional `dark` override. `color` and `uiColor` resolve to the right variant automatically based on the active trait collection, so most callers never need to touch the components directly:
///
/// ```swift
/// import NucleusColors
/// import SwiftUI
///
/// Text("Hello")
///     .foregroundStyle(NucleusColor.textPrimary.color)
///     .background(NucleusColor.surfacePrimary.color)
/// ```
public struct NucleusColor: Sendable {
    public let light: ColorComponents
    public let dark: ColorComponents?

    /// Creates a color whose light- and dark-mode appearance are the same.
    public init(_ components: ColorComponents) {
        self.light = components
        self.dark = nil
    }

    /// Creates a color with separate appearances for light and dark mode.
    public init(light: ColorComponents, dark: ColorComponents) {
        self.light = light
        self.dark = dark
    }

    internal init(hex: String) {
        self.init(NucleusColor.parseHex(hex))
    }

    internal init(light: String, dark: String) {
        self.init(
            light: NucleusColor.parseHex(light),
            dark: NucleusColor.parseHex(dark)
        )
    }

    /// A `UIColor` that resolves to the light or dark components based on the current trait collection. Returns a static color when no dark variant is set.
    public var uiColor: UIColor {
        guard let dark else { return UIColor(light) }
        return UIColor { traitCollection in
            switch traitCollection.userInterfaceStyle {
            case .dark: UIColor(dark)
            default: UIColor(light)
            }
        }
    }

    /// A SwiftUI `Color` backed by ``uiColor``, so it picks up trait-collection changes the same way.
    public var color: Color {
        Color(uiColor: uiColor)
    }
}

// MARK: - Helpers

/// Linear sRGB color components in the 0...1 range, with premultiplied-alpha behaviour matching `UIColor(red:green:blue:alpha:)`.
public typealias ColorComponents = (r: Double, g: Double, b: Double, a: Double)

private extension UIColor {
    convenience init(_ components: ColorComponents) {
        self.init(red: components.r, green: components.g, blue: components.b, alpha: components.a)
    }
}

private extension NucleusColor {
    static func parseHex(_ hex: String) -> ColorComponents {
        let trimmed = hex.hasPrefix("#") ? String(hex.dropFirst()) : hex
        precondition(
            trimmed.count == 6 || trimmed.count == 8,
            "NucleusColor expects a 6- or 8-character hex string, got '\(hex)'"
        )
        let padded = trimmed.count == 6 ? trimmed + "FF" : trimmed
        guard let value = UInt32(padded, radix: 16) else {
            preconditionFailure("NucleusColor got invalid hex characters in '\(hex)'")
        }
        let red = Double((value >> 24) & 0xFF) / 255
        let green = Double((value >> 16) & 0xFF) / 255
        let blue = Double((value >> 8) & 0xFF) / 255
        let alpha = Double(value & 0xFF) / 255
        return (red, green, blue, alpha)
    }
}
