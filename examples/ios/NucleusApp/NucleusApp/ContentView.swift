import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            ScrollView {
                LazyVStack(alignment: .leading, spacing: 24) {
                    ForEach(primitiveGroups) { group in
                        VStack(alignment: .leading, spacing: 12) {
                            Text(group.name)
                                .font(.headline)
                                .foregroundStyle(Color(hex: AppNucleusPrimitiveColors.grey950))

                            ForEach(group.tokens) { token in
                                HStack(spacing: 12) {
                                    RoundedRectangle(cornerRadius: 12)
                                        .fill(Color(hex: token.hex))
                                        .frame(width: 44, height: 44)
                                        .overlay(
                                            RoundedRectangle(cornerRadius: 12)
                                                .stroke(Color(hex: AppNucleusPrimitiveColors.grey200), lineWidth: 1)
                                        )

                                    VStack(alignment: .leading, spacing: 4) {
                                        Text(token.name)
                                            .font(.body)
                                            .foregroundStyle(Color(hex: AppNucleusPrimitiveColors.grey950))
                                        Text("#\(token.hex)")
                                            .font(.caption)
                                            .foregroundStyle(Color(hex: AppNucleusPrimitiveColors.grey700))
                                    }

                                    Spacer()
                                }
                                .padding(14)
                                .background(Color(hex: AppNucleusPrimitiveColors.grey0))
                                .clipShape(RoundedRectangle(cornerRadius: 16))
                            }
                        }
                    }
                }
                .padding(16)
            }
            .background(Color(hex: AppNucleusPrimitiveColors.grey50))
            .navigationTitle("Nucleus")
        }
        .navigationViewStyle(.stack)
    }
}

private struct TokenGroup: Identifiable {
    let id: String
    let name: String
    let tokens: [TokenSwatch]
}

private struct TokenSwatch: Identifiable {
    let id: String
    let name: String
    let hex: String
}

private let primitiveGroups: [TokenGroup] = [
    TokenGroup(
        id: "grey",
        name: "Grey",
        tokens: [
            TokenSwatch(id: "grey0", name: "grey0", hex: AppNucleusPrimitiveColors.grey0),
            TokenSwatch(id: "grey50", name: "grey50", hex: AppNucleusPrimitiveColors.grey50),
            TokenSwatch(id: "grey100", name: "grey100", hex: AppNucleusPrimitiveColors.grey100),
            TokenSwatch(id: "grey200", name: "grey200", hex: AppNucleusPrimitiveColors.grey200),
            TokenSwatch(id: "grey300", name: "grey300", hex: AppNucleusPrimitiveColors.grey300),
            TokenSwatch(id: "grey400", name: "grey400", hex: AppNucleusPrimitiveColors.grey400),
            TokenSwatch(id: "grey500", name: "grey500", hex: AppNucleusPrimitiveColors.grey500),
            TokenSwatch(id: "grey600", name: "grey600", hex: AppNucleusPrimitiveColors.grey600),
            TokenSwatch(id: "grey700", name: "grey700", hex: AppNucleusPrimitiveColors.grey700),
            TokenSwatch(id: "grey800", name: "grey800", hex: AppNucleusPrimitiveColors.grey800),
            TokenSwatch(id: "grey900", name: "grey900", hex: AppNucleusPrimitiveColors.grey900),
            TokenSwatch(id: "grey950", name: "grey950", hex: AppNucleusPrimitiveColors.grey950),
        ]
    ),
    TokenGroup(
        id: "error",
        name: "Error",
        tokens: [
            TokenSwatch(id: "error100", name: "error100", hex: AppNucleusPrimitiveColors.error100),
            TokenSwatch(id: "error200", name: "error200", hex: AppNucleusPrimitiveColors.error200),
            TokenSwatch(id: "error300", name: "error300", hex: AppNucleusPrimitiveColors.error300),
            TokenSwatch(id: "error400", name: "error400", hex: AppNucleusPrimitiveColors.error400),
            TokenSwatch(id: "error500", name: "error500", hex: AppNucleusPrimitiveColors.error500),
            TokenSwatch(id: "error600", name: "error600", hex: AppNucleusPrimitiveColors.error600),
            TokenSwatch(id: "error700", name: "error700", hex: AppNucleusPrimitiveColors.error700),
            TokenSwatch(id: "error800", name: "error800", hex: AppNucleusPrimitiveColors.error800),
            TokenSwatch(id: "error900", name: "error900", hex: AppNucleusPrimitiveColors.error900),
        ]
    ),
    TokenGroup(
        id: "warning",
        name: "Warning",
        tokens: [
            TokenSwatch(id: "warning100", name: "warning100", hex: AppNucleusPrimitiveColors.warning100),
            TokenSwatch(id: "warning200", name: "warning200", hex: AppNucleusPrimitiveColors.warning200),
            TokenSwatch(id: "warning300", name: "warning300", hex: AppNucleusPrimitiveColors.warning300),
            TokenSwatch(id: "warning400", name: "warning400", hex: AppNucleusPrimitiveColors.warning400),
            TokenSwatch(id: "warning500", name: "warning500", hex: AppNucleusPrimitiveColors.warning500),
            TokenSwatch(id: "warning600", name: "warning600", hex: AppNucleusPrimitiveColors.warning600),
            TokenSwatch(id: "warning700", name: "warning700", hex: AppNucleusPrimitiveColors.warning700),
            TokenSwatch(id: "warning800", name: "warning800", hex: AppNucleusPrimitiveColors.warning800),
            TokenSwatch(id: "warning900", name: "warning900", hex: AppNucleusPrimitiveColors.warning900),
        ]
    ),
    TokenGroup(
        id: "success",
        name: "Success",
        tokens: [
            TokenSwatch(id: "success100", name: "success100", hex: AppNucleusPrimitiveColors.success100),
            TokenSwatch(id: "success200", name: "success200", hex: AppNucleusPrimitiveColors.success200),
            TokenSwatch(id: "success300", name: "success300", hex: AppNucleusPrimitiveColors.success300),
            TokenSwatch(id: "success400", name: "success400", hex: AppNucleusPrimitiveColors.success400),
            TokenSwatch(id: "success500", name: "success500", hex: AppNucleusPrimitiveColors.success500),
            TokenSwatch(id: "success600", name: "success600", hex: AppNucleusPrimitiveColors.success600),
            TokenSwatch(id: "success700", name: "success700", hex: AppNucleusPrimitiveColors.success700),
            TokenSwatch(id: "success800", name: "success800", hex: AppNucleusPrimitiveColors.success800),
            TokenSwatch(id: "success900", name: "success900", hex: AppNucleusPrimitiveColors.success900),
        ]
    ),
    TokenGroup(
        id: "info",
        name: "Info",
        tokens: [
            TokenSwatch(id: "info100", name: "info100", hex: AppNucleusPrimitiveColors.info100),
            TokenSwatch(id: "info200", name: "info200", hex: AppNucleusPrimitiveColors.info200),
            TokenSwatch(id: "info300", name: "info300", hex: AppNucleusPrimitiveColors.info300),
            TokenSwatch(id: "info400", name: "info400", hex: AppNucleusPrimitiveColors.info400),
            TokenSwatch(id: "info500", name: "info500", hex: AppNucleusPrimitiveColors.info500),
            TokenSwatch(id: "info600", name: "info600", hex: AppNucleusPrimitiveColors.info600),
            TokenSwatch(id: "info700", name: "info700", hex: AppNucleusPrimitiveColors.info700),
            TokenSwatch(id: "info800", name: "info800", hex: AppNucleusPrimitiveColors.info800),
            TokenSwatch(id: "info900", name: "info900", hex: AppNucleusPrimitiveColors.info900),
        ]
    ),
]

#Preview {
    ContentView()
}
