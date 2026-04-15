import SwiftUI
import UIKit

public struct WLDColor: Sendable {
    var light: (hex: String, opacity: Double)?
    var dark: (hex: String, opacity: Double)?

    public init(light: String, dark: String) {
        self.light = Self.parseHex(light)
        self.dark = Self.parseHex(dark)
    }

    init(light: (hex: String, opacity: Double), dark: (hex: String, opacity: Double)) {
        self.light = light
        self.dark = dark
    }

    public init(_ hex: String, opacity: Double? = 1) {
        light = (hex, opacity ?? 1)
    }

    public var uiColor: UIColor {
        // light is the default if 2 colors were not given
        guard let light else { return .white }
        guard let dark else { return UIColor(hex: light.hex).withAlphaComponent(light.opacity) }

        return UIColor { traitCollection in
            switch traitCollection.userInterfaceStyle {
            case .dark:
                return UIColor(hex: dark.hex).withAlphaComponent(dark.opacity)
            default:
                return UIColor(hex: light.hex).withAlphaComponent(light.opacity)
            }
        }
    }

    public var color: Color {
        return Color(uiColor: uiColor)
    }

    public var hex: String {
        return dark?.hex ?? light!.hex
    }

    private static func parseHex(_ hex: String) -> (hex: String, opacity: Double) {
        if hex.count == 8 {
            let colorHex = String(hex.prefix(6))
            let alphaHex = String(hex.suffix(2))
            let alpha = Double(Int(alphaHex, radix: 16) ?? 255) / 255.0
            return (colorHex, alpha)
        }
        return (hex, 1.0)
    }
}
