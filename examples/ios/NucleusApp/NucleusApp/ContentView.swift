import SwiftUI

struct ContentView: View {
    @State private var selectedSectionID = demoSections.first?.id ?? "primitive"
    @State private var appearance: DemoAppearance = .dark

    private var selectedSection: DemoSection {
        demoSections.first(where: { $0.id == selectedSectionID }) ?? demoSections[0]
    }

    private var chromeTheme: SemanticTheme {
        appearance.theme
    }

    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 24) {
                    HeroSection(
                        theme: chromeTheme,
                        selectedAppearance: $appearance,
                        selectedSectionID: $selectedSectionID
                    )

                    TokenSection(
                        section: selectedSection,
                        theme: chromeTheme
                    )
                }
                .padding(16)
            }
            .background(Color(hex: chromeTheme.background))
        }
        .navigationViewStyle(.stack)
        .preferredColorScheme(appearance.colorScheme)
    }
}

#Preview {
    ContentView()
}
