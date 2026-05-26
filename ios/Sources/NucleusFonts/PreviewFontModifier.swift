#if DEBUG
// MARK: Copied from world-app-ios. Only used for SwiftUI previews in this package.

import SwiftUI

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
