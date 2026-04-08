import SwiftUI
import UIKit

struct HeroSection: View {
    let theme: SemanticTheme
    @Binding var selectedAppearance: DemoAppearance
    @Binding var selectedSectionID: String

    var body: some View {
        HStack(alignment: .center, spacing: 12) {
            Text("Nucleus")
                .font(.largeTitle.weight(.semibold))
                .foregroundStyle(Color(hex: theme.text))

            Spacer(minLength: 0)

            Picker("Appearance", selection: $selectedAppearance) {
                ForEach(DemoAppearance.allCases) { option in
                    Text(option.name).tag(option)
                }
            }
            .pickerStyle(.segmented)
            .frame(maxWidth: 180)

            Menu {
                ForEach(demoSections) { section in
                    Button {
                        selectedSectionID = section.id
                    } label: {
                        if section.id == selectedSectionID {
                            Label(section.label, systemImage: "checkmark")
                        } else {
                            Text(section.label)
                        }
                    }
                }
            } label: {
                Image(systemName: "line.3.horizontal")
                    .font(.headline.weight(.semibold))
                    .foregroundStyle(Color(hex: theme.text))
                    .frame(width: 44, height: 44)
                    .overlay(
                        RoundedRectangle(cornerRadius: 22)
                            .stroke(Color(hex: theme.border), lineWidth: 1)
                    )
            }
        }
    }
}

struct TokenSection: View {
    let section: DemoSection
    let theme: SemanticTheme

    var body: some View {
        VStack(alignment: .leading, spacing: 24) {
            ForEach(section.groups) { group in
                VStack(alignment: .leading, spacing: 12) {
                    Text(group.name)
                        .font(.caption.weight(.semibold))
                        .foregroundStyle(Color(hex: theme.muted))

                    VStack(spacing: 10) {
                        ForEach(group.tokens) { token in
                            TokenCard(token: token, theme: theme)
                        }
                    }
                }
            }
        }
    }
}

struct TokenCard: View {
    let token: TokenSwatch
    let theme: SemanticTheme
    @State private var copied = false

    var body: some View {
        Button {
            UIPasteboard.general.string = "#\(token.hex)"
            copied = true
            DispatchQueue.main.asyncAfter(deadline: .now() + 1.2) {
                copied = false
            }
        } label: {
            HStack(spacing: 12) {
                RoundedRectangle(cornerRadius: 12)
                    .fill(Color(hex: token.hex))
                    .frame(width: 44, height: 44)
                    .overlay(
                        RoundedRectangle(cornerRadius: 12)
                            .stroke(Color(hex: theme.border), lineWidth: 1)
                    )

                VStack(alignment: .leading, spacing: 4) {
                    Text(token.name)
                        .font(.body.weight(.medium))
                        .foregroundStyle(Color(hex: theme.text))
                    Text("#\(token.hex)")
                        .font(.caption.monospaced())
                        .foregroundStyle(Color(hex: theme.muted))
                }

                Spacer()

                Text(copied ? "Copied" : "Copy")
                    .font(.caption.monospaced())
                    .foregroundStyle(Color(hex: theme.muted))
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            .padding(14)
            .background(Color(hex: theme.surfaceAlt))
            .overlay(
                RoundedRectangle(cornerRadius: 18)
                    .stroke(Color(hex: theme.border), lineWidth: 1)
            )
            .clipShape(RoundedRectangle(cornerRadius: 18))
            .contentShape(Rectangle())
        }
        .buttonStyle(.plain)
    }
}
