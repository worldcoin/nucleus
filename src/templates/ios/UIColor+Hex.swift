import SwiftUI
import UIKit

public extension Color {
    init(hex: String) {
        self.init(UIColor.init(hex: hex))
    }

    static func hex(_ hex: String) -> Color {
        return self.init(hex: hex)
    }
}

public extension UIColor {
    convenience init(hex: Int, alpha: CGFloat = 1) {
        self.init(
            red: CGFloat((hex & 0xFF0000) >> 16) / 255.0,
            green: CGFloat((hex & 0x00FF00) >> 8) / 255.0,
            blue: CGFloat(hex & 0x0000FF) / 255.0,
            alpha: alpha
        )
    }

    convenience init(hex: String, alpha: CGFloat = 1) {
        let stripped = hex.trimmingCharacters(in: CharacterSet(charactersIn: "#"))
        let hexInt = UIColor.int(from: stripped)

        // 8-character hex strings encode alpha in the last two characters (RRGGBBAA)
        if stripped.count == 8 {
            let red = CGFloat((hexInt & 0xFF000000) >> 24) / 255.0
            let green = CGFloat((hexInt & 0x00FF0000) >> 16) / 255.0
            let blue = CGFloat((hexInt & 0x0000FF00) >> 8) / 255.0
            let embeddedAlpha = CGFloat(hexInt & 0x000000FF) / 255.0
            self.init(red: red, green: green, blue: blue, alpha: embeddedAlpha)
        } else {
            let red = CGFloat((hexInt & 0xFF0000) >> 16) / 255.0
            let green = CGFloat((hexInt & 0xFF00) >> 8) / 255.0
            let blue = CGFloat(hexInt & 0xFF) / 255.0
            self.init(red: red, green: green, blue: blue, alpha: alpha)
        }
    }

    private static func int(from hex: String) -> Int {
        var hexInt: UInt64 = 0
        let scanner = Scanner(string: hex)
        scanner.charactersToBeSkipped = CharacterSet(charactersIn: "#")
        scanner.scanHexInt64(&hexInt)

        return Int(hexInt)
    }

    func toHex() -> String {
        var r: CGFloat = 0
        var g: CGFloat = 0
        var b: CGFloat = 0
        var a: CGFloat = 0

        getRed(&r, green: &g, blue: &b, alpha: &a)

        let rgb: Int = (Int)(r * 255) << 16 | (Int)(g * 255) << 8 | (Int)(b * 255) << 0

        return String(format: "#%06x", rgb)
    }
}

public extension UIColor {
    var rgba: (red: CGFloat, green: CGFloat, blue: CGFloat, alpha: CGFloat) {
        var red: CGFloat = 0
        var green: CGFloat = 0
        var blue: CGFloat = 0
        var alpha: CGFloat = 0
        getRed(&red, green: &green, blue: &blue, alpha: &alpha)

        return (red, green, blue, alpha)
    }
}

// MARK: - String conversions

public extension String {
    func toUIColor() -> UIColor {
        UIColor(hex: self)
    }

    func toColor() -> Color {
        Color(hex: self)
    }
}

public extension Color {
    /// Hex representation "#RRGGBB"
    var hexValue: String? {
        let uiColor = UIColor(self)
        var r: CGFloat = 0
        var g: CGFloat = 0
        var b: CGFloat = 0
        var a: CGFloat = 0
        guard uiColor.getRed(&r, green: &g, blue: &b, alpha: &a) else { return nil }

        return String(format: "#%02X%02X%02X", Int(r * 255), Int(g * 255), Int(b * 255))
    }
}
