import Foundation

public enum SDUIv3 {
    enum AspectRatio: String, Codable, Sendable, Equatable {
        case landscape = "Landscape"
        case square = "Square"
        case portrait = "Portrait"
    }

    enum TextPlacement: String, Codable, Sendable, Equatable {
        case below = "Below"
        case overlay = "Overlay"
    }

    enum Chrome: String, Codable, Sendable, Equatable {
        case none = "None"
        case border = "Border"
    }

    enum Theme: String, Codable, Sendable, Equatable {
        case light = "Light"
        case dark = "Dark"
    }

    enum CardSize: String, Codable, Sendable, Equatable {
        case small = "Small"
        case medium = "Medium"
        case large = "Large"
    }
}
