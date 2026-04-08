import SwiftUI

struct TokenSwatch: Identifiable {
    let id: String
    let name: String
    let hex: String
}

struct TokenGroup: Identifiable {
    let id: String
    let name: String
    let tokens: [TokenSwatch]
}

struct SemanticTheme {
    let background: String
    let surface: String
    let surfaceAlt: String
    let border: String
    let text: String
    let muted: String
    let accent: String
    let accentContent: String
}

struct SemanticMode: Identifiable {
    let id: String
    let name: String
    let theme: SemanticTheme
    let groups: [TokenGroup]
}

struct DemoSection: Identifiable {
    let id: String
    let label: String
    let groups: [TokenGroup]
}

enum DemoAppearance: String, CaseIterable, Identifiable {
    case light
    case dark

    var id: String { rawValue }

    var name: String {
        switch self {
        case .light:
            return "Light"
        case .dark:
            return "Dark"
        }
    }

    var colorScheme: ColorScheme {
        switch self {
        case .light:
            return .light
        case .dark:
            return .dark
        }
    }

    var theme: SemanticTheme {
        semanticModes.first(where: { $0.id == rawValue })?.theme ?? neutralPalette
    }
}
