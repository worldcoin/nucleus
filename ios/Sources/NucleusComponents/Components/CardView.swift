import SwiftUI
import NucleusColors
import NucleusFonts

public extension SDUIv3 {
    struct CardView<C: NucelusUIContext>: View {
        // content
        private let title: String
        private let subtitle: String?
        private let imageUrl: URL?
        private let logoUrl: URL?
        private let ctaTitle: String?
        private let onTap: (() -> Void)?
        private let onCtaTap: (() -> Void)?

        // style
        private let size: CardSize
        private let aspectRatio: AspectRatio
        private let textPlacement: TextPlacement
        private let titleFont: NucleusFont
        private let subtitleFont: NucleusFont
        private let chrome: Chrome
        private let theme: Theme
        private let cornerRadius: CGFloat
        private let borderWidth: CGFloat?
        private let borderColor: NucleusColor?
        private let uiContext: C

        // helpers
        private var isLargeSquare: Bool { size == .large && aspectRatio == .square }
        private var textSpacing: CGFloat { isLargeSquare ? 6 : 2 }
        private var textPadding: CGFloat { isLargeSquare ? 24 : 16 }

        private var titleColor: NucleusColor {
            switch theme {
            case .dark: .grey950
            case .light: .white
            }
        }

        private var subtitleColor: NucleusColor {
            switch theme {
            case .dark: .grey700
            case .light: NucleusColor((1,1,1,0.6))
            }
        }

        private var ctaTitleColor: NucleusColor {
            switch theme {
            case .dark: .white
            case .light: .grey950
            }
        }

        private var ctaBackgroundColor: NucleusColor {
            switch theme {
            case .dark: .grey950
            case .light: .white
            }
        }

        private static var horizontalPadding: CGFloat { 24 }
        private static var cardHorizontalPadding: CGFloat { horizontalPadding * 2 }
        private static var textsHeight: CGFloat { 66 }
        private static var landscapeWidthRatio: CGFloat { 1.37 }
        private static var portraitWidthRatio: CGFloat { 0.7 }
        private static var landscapeOverlayHeight: CGFloat { 219 }

        private var cardWidth: CGFloat {
            switch size {
            case .small: 167
            case .medium: 230
            case .large: UIScreen.main.bounds.width - Self.cardHorizontalPadding
            }
        }

        private var cardImageAspectRatio: CGFloat {
            switch size {
            case .small: 167.0 / 122.0
            case .medium: 230.0 / 168.0
            case .large: 345.0 / 219.0
            }
        }

        private var imageHeight: CGFloat {
            switch textPlacement {
            case .below:
                switch aspectRatio {
                case .landscape: cardWidth / cardImageAspectRatio
                case .square: cardWidth
                case .portrait: cardWidth / Self.portraitWidthRatio
                }
            case .overlay:
                switch aspectRatio {
                case .landscape: Self.landscapeOverlayHeight
                case .square: UIScreen.main.bounds.width - Self.cardHorizontalPadding
                case .portrait: cardWidth / Self.portraitWidthRatio
                }
            }
        }

        private var cardHeight: CGFloat {
            switch textPlacement {
            case .below:
                switch aspectRatio {
                case .landscape: (cardWidth / Self.landscapeWidthRatio) + Self.textsHeight
                case .square: cardWidth
                case .portrait: cardWidth / Self.portraitWidthRatio
                }
            case .overlay:
                switch aspectRatio {
                case .landscape: Self.landscapeOverlayHeight
                case .square: UIScreen.main.bounds.width - Self.cardHorizontalPadding
                case .portrait: cardWidth / Self.portraitWidthRatio
                }
            }
        }

        init(
            title: String,
            subtitle: String? = nil,
            imageUrl: URL? = nil,
            logoUrl: URL? = nil,
            ctaTitle: String? = nil,
            onTap: (() -> Void)? = nil,
            onCtaTap: (() -> Void)? = nil,
            size: CardSize,
            aspectRatio: AspectRatio,
            textPlacement: TextPlacement,
            titleFont: NucleusFont,
            subtitleFont: NucleusFont,
            chrome: Chrome,
            theme: Theme,
            cornerRadius: CGFloat? = nil,
            borderWidth: CGFloat? = nil,
            borderColor: NucleusColor? = nil,
            uiContext: C
        ) {
            self.title = title
            self.subtitle = subtitle
            self.imageUrl = imageUrl
            self.logoUrl = logoUrl
            self.ctaTitle = ctaTitle
            self.onTap = onTap
            self.onCtaTap = onCtaTap
            self.size = size
            self.aspectRatio = aspectRatio
            self.textPlacement = textPlacement
            self.titleFont = titleFont
            self.subtitleFont = subtitleFont
            self.chrome = chrome
            self.theme = theme
            self.cornerRadius = cornerRadius ?? 16
            self.borderWidth = borderWidth
            self.borderColor = borderColor
            self.uiContext = uiContext
        }

        public var body: some View {
            Group {
                if let onTap {
                    Button {
                        onTap()
                    } label: {
                        cardContent
                    }
                    .buttonStyle(.plain)
                } else {
                    cardContent
                }
            }
            .frame(maxWidth: .infinity)
        }

        private var cardContent: some View {
            Group {
                switch textPlacement {
                case .below:
                    VStack(spacing: 0) {
                        RoundedRectangle(cornerRadius: cornerRadius)
                            .foregroundStyle(NucleusColor.grey200.color)
                            .overlay {
                                if let imageUrl {
                                    uiContext.remoteImageView(url: imageUrl)
                                        .scaledToFill()
                                }
                            }
                            .cornerRadius(cornerRadius)
                            .frame(height: imageHeight)
                        textsRow
                            .padding(.top, 12)
                        Spacer()
                    }
                    .frame(width: cardWidth)
                case .overlay:
                    VStack {
                        Spacer()
                        textsRow
                    }
                    .padding(textPadding)
                    .frame(width: cardWidth)
                    .frame(height: cardHeight)
                    .background {
                        if let imageUrl {
                            uiContext.remoteImageView(url: imageUrl)
                                .scaledToFill()
                                .frame(width: cardWidth)
                        }
                    }
                    .clipped()
                    .cornerRadius(cornerRadius)
                }
            }
            .overlay {
                if chrome == .border {
                    RoundedRectangle(cornerRadius: cornerRadius)
                        .stroke(
                            (borderColor ?? .grey950).color,
                            style: .init(lineWidth: borderWidth ?? 1)
                        )
                }
            }
        }

        private var textsRow: some View {
            HStack {
                if let logoUrl {
                    uiContext.remoteImageView(url: logoUrl)
                        .scaledToFill()
                        .frame(width: 40, height: 40)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                }
                VStack(alignment: .leading, spacing: textSpacing) {
                    Text(title)
                        .modifier(uiContext.fontModifier(font: titleFont))
                        .foregroundStyle(titleColor.color)
                        .frame(maxWidth: .infinity, alignment: .leading)
                        .lineLimit(2)
                    if let subtitle {
                        Text(subtitle)
                            .modifier(uiContext.fontModifier(font: subtitleFont))
                            .foregroundStyle(subtitleColor.color)
                            .frame(maxWidth: .infinity, alignment: .leading)
                            .lineLimit(2)
                    }
                }
                ctaButton
            }
        }

        @ViewBuilder
        private var ctaButton: some View {
            if let ctaTitle {
                Button {
                    onCtaTap?()
                } label: {
                    Text(ctaTitle)
                        .modifier(uiContext.fontModifier(font: .l2))
                        .foregroundStyle(ctaTitleColor.color)
                        .padding(.horizontal, 12)
                        .padding(.vertical, 8)
                        .background {
                            Capsule()
                                .foregroundStyle(ctaBackgroundColor.color)
                        }
                }
            }
        }
    }
}

#if DEBUG
#Preview {
    SDUIv3.CardView(
        title: "Title goes here",
        subtitle: "Subtitle goes here",
        ctaTitle: "Verify",
        size: .large,
        aspectRatio: .landscape,
        textPlacement: .overlay,
        titleFont: .s1,
        subtitleFont: .b3,
        chrome: .none,
        theme: .dark,
        uiContext: PreviewUIContext()
    )
}
#endif
