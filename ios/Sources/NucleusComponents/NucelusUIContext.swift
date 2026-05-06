import Foundation
import SwiftUI
import NucleusFonts

@MainActor
public protocol NucelusUIContext {
    associatedtype FontModifier: ViewModifier
    associatedtype RemoteImageView: View

    func fontModifier(font: NucleusFont) -> FontModifier
    func remoteImageView(url: URL) -> RemoteImageView
}

#if DEBUG
internal struct PreviewUIContext: NucelusUIContext {
    func fontModifier(font: NucleusFont) -> some ViewModifier {
        PreviewFontModifier(font: font)
    }

    func remoteImageView(url: URL) -> some View {
        AsyncImage(url: url) { image in
            image.image?.resizable()
        }
    }
}
#endif
