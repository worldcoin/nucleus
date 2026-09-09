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
            TypographyGalleryView()
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
                    Text("17 pt · Weight 350 · Tracking −0.5% · Line height ×1.3")
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

private enum GalleryPage: String, CaseIterable, Identifiable {
    case all = "All styles"
    case paragraphs = "b1 paragraphs"
    var id: Self { self }
}

private struct TypographyStyle: Identifiable {
    let id: String
    let category: String
    let current: NucleusFont
    let previous: NucleusFont

    var sample: String {
        switch category {
        case "Display": "A world\nfor everyone"
        case "Headline": "A world for everyone.\nBuilt on human connection."
        case "Subtitle": "Real people. Shared possibilities.\nMake room for what comes next."
        case "Label": "Continue with World\nExplore your possibilities"
        case "Body": "The world is full of real people with different stories, ideas, and experiences. A shared network should make it easier for everyone to take part.\n\nGood typography gives those stories room to breathe. Look closely at words like quietly, journey, café, naïve, and mañana."
        default: "Your information stays in your control.\nLast updated today · 09:41 · café, naïve, mañana"
        }
    }

    // Frozen v0.2.9 values from main at b21e737, before the font and Figma alignment.
    static let all: [TypographyStyle] = [
        TypographyStyle(
            id: "d1", category: "Display", current: .d1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 56,
                weight: 550, letterSpacing: -0.02, lineHeight: 1.0,
                dynamicTypeStyle: .largeTitle)
        ),
        TypographyStyle(
            id: "h1", category: "Headline", current: .h1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 30,
                weight: 500, letterSpacing: -0.015, lineHeight: 1.2,
                dynamicTypeStyle: .largeTitle)
        ),
        TypographyStyle(
            id: "h2", category: "Headline", current: .h2,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 27,
                weight: 500, letterSpacing: -0.015, lineHeight: 1.2,
                dynamicTypeStyle: .title1)
        ),
        TypographyStyle(
            id: "h3", category: "Headline", current: .h3,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 24,
                weight: 500, letterSpacing: -0.015, lineHeight: 1.2,
                dynamicTypeStyle: .title1)
        ),
        TypographyStyle(
            id: "h4", category: "Headline", current: .h4,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 21,
                weight: 500, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .title2)
        ),
        TypographyStyle(
            id: "h5", category: "Headline", current: .h5,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 19,
                weight: 500, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .headline)
        ),
        TypographyStyle(
            id: "s1", category: "Subtitle", current: .s1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 17,
                weight: 450, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .headline)
        ),
        TypographyStyle(
            id: "s2", category: "Subtitle", current: .s2,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 15,
                weight: 450, letterSpacing: 0.0, lineHeight: 1.2,
                dynamicTypeStyle: .subheadline)
        ),
        TypographyStyle(
            id: "s3", category: "Subtitle", current: .s3,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 13,
                weight: 450, letterSpacing: 0.0, lineHeight: 1.2,
                dynamicTypeStyle: .footnote)
        ),
        TypographyStyle(
            id: "l1", category: "Label", current: .l1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 17,
                weight: 550, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .headline)
        ),
        TypographyStyle(
            id: "l2", category: "Label", current: .l2,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 15,
                weight: 550, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .subheadline)
        ),
        TypographyStyle(
            id: "l3", category: "Label", current: .l3,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 13,
                weight: 550, letterSpacing: -0.01, lineHeight: 1.2,
                dynamicTypeStyle: .footnote)
        ),
        TypographyStyle(
            id: "b1", category: "Body", current: .b1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 17,
                weight: 350, letterSpacing: 0.0, lineHeight: 1.3,
                dynamicTypeStyle: .body)
        ),
        TypographyStyle(
            id: "b2", category: "Body", current: .b2,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 15,
                weight: 350, letterSpacing: 0.0, lineHeight: 1.3,
                dynamicTypeStyle: .subheadline)
        ),
        TypographyStyle(
            id: "b3", category: "Body", current: .b3,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 13,
                weight: 350, letterSpacing: 0.0, lineHeight: 1.2,
                dynamicTypeStyle: .footnote)
        ),
        TypographyStyle(
            id: "c1", category: "Caption", current: .c1,
            previous: NucleusFont(fontName: "WorldProMVPLH-Regular", size: 11,
                weight: 350, letterSpacing: 0.0, lineHeight: 1.4,
                dynamicTypeStyle: .caption2)
        )
    ]
}

private struct TypographyGalleryView: View {
    @State private var page: GalleryPage = .all
    @State private var mode: ComparisonMode = .both

    var body: some View {
        VStack(spacing: 0) {
            Picker("Typography view", selection: $page) {
                ForEach(GalleryPage.allCases) { item in
                    Text(item.rawValue).tag(item)
                }
            }
            .pickerStyle(.segmented)
            .frame(maxWidth: 420)
            .padding()

            if page == .all {
                ScrollView {
                    LazyVStack(alignment: .leading, spacing: 24) {
                        VStack(alignment: .leading, spacing: 8) {
                            Text("World Pro · Typography")
                                .font(.largeTitle.bold())
                                .accessibilityAddTraits(.isHeader)
                            Text("All 16 styles · Previous release → UI Kit 5.0")
                                .font(.headline)
                            Text("Previous uses the original font and tokens. Updated uses the new font and Figma-aligned tokens. Both follow the existing Nucleus iOS preview spacing.")
                                .font(.subheadline)
                                .foregroundStyle(.secondary)
                            Text("Dynamic Type follows the device setting. The values shown are base sizes; rendered line spacing can differ from Figma.")
                                .font(.footnote)
                                .foregroundStyle(.secondary)
                        }
                        Picker("Versions to show", selection: $mode) {
                            ForEach(ComparisonMode.allCases) { item in
                                Text(item.rawValue).tag(item)
                            }
                        }
                        .pickerStyle(.segmented)
                        .frame(maxWidth: 420)

                        ForEach(TypographyStyle.all) { style in
                            TypographyRow(style: style, mode: mode)
                        }
                    }
                    .padding(24)
                    .frame(maxWidth: 1100, alignment: .leading)
                    .frame(maxWidth: .infinity)
                }
                .accessibilityIdentifier("typographyGallery")
            } else {
                ComparisonView()
            }
        }
        .background(Color(uiColor: .systemGroupedBackground))
    }
}

private struct TypographyRow: View {
    let style: TypographyStyle
    let mode: ComparisonMode
    @Environment(\.horizontalSizeClass) private var horizontalSizeClass

    private var layout: AnyLayout {
        horizontalSizeClass == .regular
            ? AnyLayout(HStackLayout(alignment: .top, spacing: 20))
            : AnyLayout(VStackLayout(alignment: .leading, spacing: 16))
    }

    var body: some View {
        VStack(alignment: .leading, spacing: 12) {
            Text("\(style.id.uppercased()) · \(style.category)")
                .font(.headline)
                .accessibilityAddTraits(.isHeader)
            layout {
                ForEach(mode.versions) { version in
                    TypographySpecimen(
                        token: version == .previous ? style.previous : style.current,
                        version: version, sample: style.sample
                    )
                    .frame(maxWidth: .infinity, alignment: .topLeading)
                }
            }
        }
        .accessibilityIdentifier("typography-\(style.id)")
    }
}

private struct TypographySpecimen: View {
    let token: NucleusFont
    let version: FontVersion
    let sample: String
    @Environment(\.dynamicTypeSize) private var dynamicTypeSize

    var body: some View {
        let traits = UITraitCollection(preferredContentSizeCategory: dynamicTypeSize.uiCategory)
        let font = token.asUIFont(compatibleWith: traits)
        let addedSpacing = font.lineHeight * (token.lineHeight.value - 1)

        VStack(alignment: .leading, spacing: 16) {
            VStack(alignment: .leading, spacing: 6) {
                Text(version.rawValue)
                    .font(.subheadline.bold())
                Text("\(token.size, specifier: "%.0f") pt · Weight \(token.weight.value)")
                    .font(.caption.monospacedDigit())
                    .foregroundStyle(.secondary)
                Text("Tracking \(token.letterSpacing * 100, specifier: "%+.1f")% · Line height \(token.lineHeight.value * 100, specifier: "%.0f")%")
                    .font(.caption.monospacedDigit())
                    .foregroundStyle(.secondary)
            }
            Text(sample)
                .font(Font(font))
                .kerning(font.pointSize * token.letterSpacing)
                .lineSpacing(addedSpacing)
                .padding(.vertical, addedSpacing / 2)
                .fixedSize(horizontal: false, vertical: true)
                .frame(maxWidth: .infinity, alignment: .leading)
        }
        .padding(20)
        .frame(maxWidth: .infinity, alignment: .leading)
        .background(Color(uiColor: .secondarySystemGroupedBackground), in: .rect(cornerRadius: 16))
    }
}
