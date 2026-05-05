import Foundation
import SwiftUI

public protocol NucleusFontRenderer {
    associatedtype SwiftUIModifier: ViewModifier
    func viewModifier(for font: NucleusFont) -> SwiftUIModifier
}

public extension View {
    func nucleusFont<R: NucleusFontRenderer>(_ font: NucleusFont, renderer: R) -> some View {
        modifier(renderer.viewModifier(for: font))
    }
}
