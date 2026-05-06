#if DEBUG
// MARK: Copied from world-app-ios. Only used for SwiftUI previews in this package.

import SwiftUI

@MainActor
private extension NucleusFont {
    func asUIFont(compatibleWith traitCollection: UITraitCollection? = nil) -> UIFont {
        Self.registerFonts()

        var descriptor: UIFontDescriptor
        if let worldPro = UIFont(name: "WorldProMVPLH-Regular", size: size) {
            descriptor = worldPro.fontDescriptor
            let worldProWeightAxisId: Int = 2003265652
            let variationAttributes: [NSNumber: Any] = [NSNumber(value: worldProWeightAxisId): Double(weight.value)]
            descriptor = descriptor.addingAttributes([
                kCTFontVariationAttribute as UIFontDescriptor.AttributeName: variationAttributes
            ])
        } else {
            descriptor = UIFont.systemFont(ofSize: size).fontDescriptor
        }
        var featureSettings: [[UIFontDescriptor.FeatureKey: Int]] = []
        if usesMonospacedDigits {
            featureSettings.append([
                UIFontDescriptor.FeatureKey.type: kNumberSpacingType,
                UIFontDescriptor.FeatureKey.selector: kMonospacedNumbersSelector,
            ])
        }
        if !featureSettings.isEmpty {
            descriptor = descriptor.addingAttributes([.featureSettings: featureSettings])
        }
        let font = UIFont(descriptor: descriptor, size: size)
        let metrics = UIFontMetrics(forTextStyle: dynamicTypeStyle)
        return metrics.scaledFont(for: font, compatibleWith: traitCollection)
    }
}

private extension View {
    func lineHeight(_ lineHeight: NucleusFont.LineHeight, uiFont: UIFont) -> some View {
        let lineSpacing = uiFont.lineHeight * (lineHeight.value - 1)

        return Group {
            if lineHeight.value >= 1 {
                self
                    .lineSpacing(uiFont.lineHeight * (lineHeight.value - 1))
                    .padding(.vertical, lineSpacing / 2)
            } else {
                self
                    .environment(\._lineHeightMultiple, lineHeight.value)
                    .padding(.top, 1 - lineSpacing)
            }
        }
    }
}

package struct PreviewFontModifier: ViewModifier {
    private var font: NucleusFont

    package init(font: NucleusFont) {
        self.font = font
    }

    package func body(content: Content) -> some View {
        let uiFont = font.asUIFont()
        let kerning = uiFont.pointSize * font.letterSpacing

        if #available(iOS 16, *) {
            return content
                .font(Font(uiFont))
                .kerning(kerning)
                .lineHeight(font.lineHeight, uiFont: uiFont)
        } else {
            return Text("Update your xcode")
        }
    }
}
#endif
