import SwiftUI
import UIKit
import CoreText
import NucleusFonts

@main
struct FontComparisonApp: App {
    init() {
        NucleusFont.registerFonts()
        guard let url = Bundle.main.url(forResource: "WorldProMVPPrevious", withExtension: "ttf") else {
            fatalError("The previous font is missing from the comparison bundle.")
        }
        var error: Unmanaged<CFError>?
        guard CTFontManagerRegisterFontsForURL(url as CFURL, .process, &error) else {
            fatalError("Could not register the previous font: \(String(describing: error?.takeRetainedValue()))")
        }
        // Fail visibly instead of accidentally comparing a system fallback.
        for name in ["WorldProMVPLH-Regular", NucleusFont.b1.fontName] {
            precondition(UIFont(name: name, size: 17) != nil, "Font not registered: \(name)")
        }
    }

    var body: some Scene {
        WindowGroup {
            ComparisonView()
        }
    }
}

private enum FontVersion: String, CaseIterable, Identifiable {
    case previous = "Previous"
    case updated = "Updated"

    var id: Self { self }

    var token: NucleusFont {
        let b1 = NucleusFont.b1
        guard self == .previous else { return b1 }
        return NucleusFont(
            fontName: "WorldProMVPLH-Regular",
            size: b1.size,
            weight: b1.weight,
            letterSpacing: b1.letterSpacing,
            lineHeight: b1.lineHeight,
            dynamicTypeStyle: b1.dynamicTypeStyle
        )
    }
}

private enum ComparisonMode: String, CaseIterable, Identifiable {
    case both = "Compare"
    case previous = "Previous"
    case updated = "Updated"

    var id: Self { self }
    var versions: [FontVersion] {
        switch self {
        case .both: FontVersion.allCases
        case .previous: [.previous]
        case .updated: [.updated]
        }
    }
}

private struct ComparisonView: View {
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass
    @State private var mode: ComparisonMode = .both

    private var layout: AnyLayout {
        horizontalSizeClass == .regular
            ? AnyLayout(HStackLayout(alignment: .top, spacing: 24))
            : AnyLayout(VStackLayout(alignment: .leading, spacing: 24))
    }

    var body: some View {
        ScrollView {
            VStack(alignment: .leading, spacing: 24) {
                VStack(alignment: .leading, spacing: 8) {
                    Text("World Pro · b1")
                        .font(.largeTitle.bold())
                        .accessibilityAddTraits(.isHeader)
                    Text("17 pt · Weight 350 · Line height ×1.3")
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                    Text("Four identical paragraphs. Equal widths. 16 pt between paragraphs.")
                        .font(.subheadline)
                        .foregroundStyle(.secondary)
                }

                Picker("Font comparison", selection: $mode) {
                    ForEach(ComparisonMode.allCases) { option in
                        Text(option.rawValue).tag(option)
                    }
                }
                .pickerStyle(.segmented)
                .frame(maxWidth: 420)

                layout {
                    ForEach(mode.versions) { version in
                        FontSample(version: version)
                            .frame(maxWidth: .infinity, alignment: .topLeading)
                    }
                }

                Text("Spacing follows the Nucleus iOS preview: native font line height × 0.3 is added between lines, with half that amount above and below each paragraph. Dynamic Type follows the device setting.")
                    .font(.footnote)
                    .foregroundStyle(.secondary)
                    .fixedSize(horizontal: false, vertical: true)
            }
            .padding(24)
            .frame(maxWidth: 960, alignment: .leading)
            .frame(maxWidth: .infinity)
        }
        .background(Color(uiColor: .systemGroupedBackground))
    }
}

private struct FontSample: View {
    let version: FontVersion
    @Environment(\.dynamicTypeSize) private var dynamicTypeSize
    @State private var measuredHeight: CGFloat = 0

    // Deliberately identical content for both fonts, including accented letters and descenders.
    private static let paragraphs = [
        "The world is full of real people with different stories, ideas, and experiences. A shared network should make it easier for everyone to take part, wherever they live and whatever language they speak.",
        "Good typography gives those stories room to breathe. As a sentence wraps onto the next line, the space between lines should feel steady. Longer passages help reveal changes that a single headline can hide.",
        "Look closely at the rhythm of this paragraph: ascending letters, descending letters, and punctuation all share the same space. Try words like typography, quietly, journey, and café, then compare naïve, mañana, and déjà vu.",
        "Every detail matters when people read on a small screen. Clear text helps them understand what happens next, check the information in front of them, and continue with confidence. The final line makes the total height easy to compare."
    ]

    var body: some View {
        // Match UIFont's Dynamic Type metrics to this SwiftUI environment.
        let traits = UITraitCollection(preferredContentSizeCategory: dynamicTypeSize.uiCategory)
        let token = version.token
        let font = token.asUIFont(compatibleWith: traits)
        let addedSpacing = font.lineHeight * (token.lineHeight.value - 1)

        VStack(alignment: .leading, spacing: 20) {
            VStack(alignment: .leading, spacing: 6) {
                Text(version.rawValue)
                    .font(.title2.bold())
                    .accessibilityAddTraits(.isHeader)
                Text(token.fontName)
                    .font(.caption.monospaced())
                    .foregroundStyle(.secondary)
                Text("Native line: \(font.lineHeight, specifier: "%.2f") pt  ·  Added: \(addedSpacing, specifier: "%.2f") pt")
                    .font(.caption)
                    .foregroundStyle(.secondary)
                Text("Text block: \(measuredHeight, specifier: "%.1f") pt")
                    .font(.caption.monospacedDigit())
                    .foregroundStyle(.secondary)
            }

            VStack(alignment: .leading, spacing: 16) {
                ForEach(Self.paragraphs, id: \.self) { paragraph in
                    Text(paragraph)
                        .font(Font(font))
                        .kerning(font.pointSize * token.letterSpacing)
                        .lineSpacing(addedSpacing)
                        .padding(.vertical, addedSpacing / 2)
                        .fixedSize(horizontal: false, vertical: true)
                        .frame(maxWidth: .infinity, alignment: .leading)
                }
            }
            .onGeometryChange(for: CGFloat.self) { proxy in
                proxy.size.height
            } action: { height in
                measuredHeight = height
            }
            .overlay(alignment: .bottom) {
                Rectangle()
                    .fill(.secondary.opacity(0.35))
                    .frame(height: 1)
                    .accessibilityHidden(true)
            }
        }
        .padding(20)
        .background(Color(uiColor: .secondarySystemGroupedBackground), in: .rect(cornerRadius: 16))
    }
}

private extension DynamicTypeSize {
    var uiCategory: UIContentSizeCategory {
        switch self {
        case .xSmall: .extraSmall
        case .small: .small
        case .medium: .medium
        case .large: .large
        case .xLarge: .extraLarge
        case .xxLarge: .extraExtraLarge
        case .xxxLarge: .extraExtraExtraLarge
        case .accessibility1: .accessibilityMedium
        case .accessibility2: .accessibilityLarge
        case .accessibility3: .accessibilityExtraLarge
        case .accessibility4: .accessibilityExtraExtraLarge
        case .accessibility5: .accessibilityExtraExtraExtraLarge
        @unknown default: .large
        }
    }
}
