import SwiftUI
import WLDColor

struct ContentView: View {
    var body: some View {
        NavigationView {
            ScrollView {
                VStack(alignment: .leading, spacing: 32) {
                    ColorSection(title: "Surface", colors: [
                        ("surfacePrimary", .surfacePrimary),
                        ("surfaceSecondary", .surfaceSecondary),
                        ("surfaceTertiary", .surfaceTertiary),
                        ("surfaceElevated", .surfaceElevated),
                    ])

                    ColorSection(title: "Text", colors: [
                        ("textPrimary", .textPrimary),
                        ("textSecondary", .textSecondary),
                        ("textTertiary", .textTertiary),
                        ("textDisabled", .textDisabled),
                    ])

                    ColorSection(title: "Action", colors: [
                        ("actionPrimary", .actionPrimary),
                        ("actionSecondary", .actionSecondary),
                        ("actionDestructive", .actionDestructive),
                        ("actionDisabled", .actionDisabled),
                    ])

                    ColorSection(title: "Status", colors: [
                        ("statusError", .statusError),
                        ("statusWarning", .statusWarning),
                        ("statusSuccess", .statusSuccess),
                        ("statusInfo", .statusInfo),
                    ])

                    ColorSection(title: "Border", colors: [
                        ("borderSubtle", .borderSubtle),
                        ("borderDefault", .borderDefault),
                        ("borderStrong", .borderStrong),
                    ])
                }
                .padding()
                .frame(maxWidth: .infinity, alignment: .leading)
            }
            .background(.surfacePrimary)
            .navigationTitle("Nucleus")
        }
        .navigationViewStyle(.stack)
    }
}

struct ColorSection: View {
    let title: String
    let colors: [(String, Color)]

    var body: some View {
        VStack(alignment: .leading, spacing: 8) {
            Text(title)
                .font(.headline)
                .foregroundStyle(.textPrimary)
                .padding(.bottom, 4)

            ForEach(colors, id: \.0) { name, color in
                ColorRow(name: name, color: color)
            }
        }
    }
}

struct ColorRow: View {
    let name: String
    let color: Color

    var body: some View {
        HStack(spacing: 12) {
            RoundedRectangle(cornerRadius: 8)
                .fill(color)
                .frame(width: 40, height: 40)
                .overlay(
                    RoundedRectangle(cornerRadius: 8)
                        .stroke(.borderDefault, lineWidth: 1)
                )

            Text(name)
                .font(.body.monospaced())
                .foregroundStyle(.textPrimary)
        }
    }
}

#Preview {
    ContentView()
}
