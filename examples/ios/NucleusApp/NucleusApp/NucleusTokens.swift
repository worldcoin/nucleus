import Foundation

private let tokenLineRegex = try! NSRegularExpression(pattern: #"^\s*public static let ([A-Za-z0-9_]+)\s*=\s*(.+)$"#)
private let tokenReferenceRegex = try! NSRegularExpression(pattern: #"^Nucleus(PrimitiveColors|SemanticColors(?:Light|Dark))\.([A-Za-z0-9_]+)$"#)
private let tokenColorRegex = try! NSRegularExpression(pattern: #"^\"([0-9A-Fa-f]{6,8})\"$"#)

private struct ResolvedToken {
    let name: String
    let value: String
    let family: String
    let tone: Int?
}

private func loadTokenPairs(fileName: String) -> [(name: String, value: String)] {
    guard let url = Bundle.main.url(forResource: fileName, withExtension: "swift"),
          let contents = try? String(contentsOf: url, encoding: .utf8) else {
        fatalError("Missing token file: \(fileName).swift")
    }

    return contents
        .components(separatedBy: .newlines)
        .compactMap { line in
            let range = NSRange(line.startIndex..<line.endIndex, in: line)
            guard let match = tokenLineRegex.firstMatch(in: line, range: range),
                  let nameRange = Range(match.range(at: 1), in: line),
                  let valueRange = Range(match.range(at: 2), in: line) else {
                return nil
            }

            return (
                name: String(line[nameRange]),
                value: String(line[valueRange])
            )
        }
}

private func referenceTokenKey(_ value: String) -> (root: String, key: String)? {
    let range = NSRange(value.startIndex..<value.endIndex, in: value)
    guard let match = tokenReferenceRegex.firstMatch(in: value, range: range),
          let rootRange = Range(match.range(at: 1), in: value),
          let pathRange = Range(match.range(at: 2), in: value) else {
        return nil
    }

    let root = value[rootRange] == "PrimitiveColors" ? "primitive" : "semantic"
    return (root: root, key: String(value[pathRange]))
}

private func colorLiteralValue(_ value: String) -> String? {
    let range = NSRange(value.startIndex..<value.endIndex, in: value)
    guard let match = tokenColorRegex.firstMatch(in: value, range: range),
          let colorRange = Range(match.range(at: 1), in: value) else {
        return nil
    }

    return String(value[colorRange]).uppercased()
}

private func resolveColorValue(
    _ value: String,
    primitiveTokens: [String: String],
    semanticTokens: [String: String]? = nil
) -> String {
    if let literal = colorLiteralValue(value) {
        return literal
    }

    guard let reference = referenceTokenKey(value) else {
        return value.replacingOccurrences(of: "#", with: "").uppercased()
    }

    let sourceTokens = reference.root == "primitive" ? primitiveTokens : semanticTokens
    guard let resolved = sourceTokens?[reference.key], resolved != value else {
        return value.replacingOccurrences(of: "#", with: "").uppercased()
    }

    return resolveColorValue(
        resolved,
        primitiveTokens: primitiveTokens,
        semanticTokens: semanticTokens
    )
}

private let primitivePairs = loadTokenPairs(fileName: "NucleusPrimitiveColors")
private let semanticLightPairs = loadTokenPairs(fileName: "NucleusSemanticColorsLight")
private let semanticDarkPairs = loadTokenPairs(fileName: "NucleusSemanticColorsDark")
private let primitiveTokenMap = Dictionary(uniqueKeysWithValues: primitivePairs)

private func tokenFamilyKey(_ name: String) -> String {
    if name == "white" || name == "black" {
        return "base"
    }

    let prefix = name.prefix { $0.isLowercase }
    return prefix.isEmpty ? name : String(prefix)
}

private func tokenTone(_ name: String) -> Int? {
    let digits = name.reversed().prefix { $0.isNumber }.reversed()
    return digits.isEmpty ? nil : Int(String(digits))
}

private func familyTitle(_ family: String) -> String {
    family.prefix(1).uppercased() + String(family.dropFirst())
}

private func resolveTokens(
    from tokens: [(name: String, value: String)],
    semanticTokens: [String: String]? = nil
) -> [ResolvedToken] {
    tokens.map { token in
        ResolvedToken(
            name: token.name,
            value: resolveColorValue(
                token.value,
                primitiveTokens: primitiveTokenMap,
                semanticTokens: semanticTokens
            ),
            family: tokenFamilyKey(token.name),
            tone: tokenTone(token.name)
        )
    }
}

private func buildTokenGroups(
    from tokens: [ResolvedToken]
) -> [TokenGroup] {
    var groupedTokens: [String: [TokenSwatch]] = [:]
    var orderedKeys: [String] = []

    for token in tokens {
        let family = token.family

        if groupedTokens[family] == nil {
            groupedTokens[family] = []
            orderedKeys.append(family)
        }

        groupedTokens[family, default: []].append(
            TokenSwatch(id: token.name, name: token.name, hex: token.value)
        )
    }

    return orderedKeys.map { key in
        TokenGroup(
            id: key,
            name: familyTitle(key),
            tokens: groupedTokens[key] ?? []
        )
    }
}

private func groupedEntries(_ entries: [ResolvedToken]) -> [String: [ResolvedToken]] {
    var groups: [String: [ResolvedToken]] = [:]

    for entry in entries {
        groups[entry.family, default: []].append(entry)
    }

    return groups
}

private func isOpaque(_ value: String) -> Bool {
    let normalized = value.replacingOccurrences(of: "#", with: "")
    return normalized.count != 8 || normalized.hasSuffix("FF")
}

private func pickOpaqueFamilyToken(_ entries: [ResolvedToken]?, index: Int = 0) -> String? {
    guard let entries else {
        return nil
    }

    let opaqueEntries = entries.filter { isOpaque($0.value) }
    guard !opaqueEntries.isEmpty else {
        return nil
    }

    return opaqueEntries.indices.contains(index) ? opaqueEntries[index].value : opaqueEntries[0].value
}

private func pickLastDistinctOpaqueToken(_ entries: [ResolvedToken]?, excluding excluded: [String]) -> String? {
    guard let entries else {
        return nil
    }

    return entries
        .filter { isOpaque($0.value) }
        .reversed()
        .first { !excluded.contains($0.value) }?
        .value
}

private func pickTone(_ entries: [ResolvedToken]?, targets: [Int]) -> String? {
    guard let entries else {
        return nil
    }

    let tonedEntries = entries.compactMap { entry -> ResolvedToken? in
        entry.tone == nil ? nil : entry
    }

    guard !tonedEntries.isEmpty else {
        return entries.first?.value
    }

    for target in targets {
        if let exact = tonedEntries.first(where: { $0.tone == target }) {
            return exact.value
        }
    }

    guard let firstTarget = targets.first else {
        return tonedEntries.first?.value
    }

    return tonedEntries.min {
        abs(($0.tone ?? firstTarget) - firstTarget) < abs(($1.tone ?? firstTarget) - firstTarget)
    }?.value
}

private func pickNeutralEntries(from groups: [String: [ResolvedToken]]) -> [ResolvedToken] {
    let candidates = ["grey", "gray", "neutral", "stone", "slate"]

    for family in candidates {
        if let entries = groups[family], entries.contains(where: { $0.tone != nil }) {
            return entries
        }
    }

    return groups.values
        .filter { $0.contains(where: { $0.tone != nil }) }
        .sorted { $0.count > $1.count }
        .first ?? []
}

private func pickAccentEntries(
    from groups: [String: [ResolvedToken]],
    neutralFamily: String
) -> [ResolvedToken] {
    let priorities = ["info", "accent", "brand", "blue"]

    for family in priorities {
        if let entries = groups[family] {
            return entries
        }
    }

    return groups.first { element in
        element.key != "base" && element.key != neutralFamily
    }?.value ?? []
}

private func contrastColor(for color: String, light: String = "FFFFFF", dark: String = "17181A") -> String {
    let normalized = color.replacingOccurrences(of: "#", with: "")
    let rgb = String(normalized.prefix(6))
    guard rgb.count == 6 else {
        return light
    }

    let red = Double(Int(rgb.prefix(2), radix: 16) ?? 0)
    let green = Double(Int(rgb.dropFirst(2).prefix(2), radix: 16) ?? 0)
    let blue = Double(Int(rgb.dropFirst(4).prefix(2), radix: 16) ?? 0)
    let luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255

    return luminance > 0.6 ? dark : light
}

private func buildNeutralPalette(from entries: [ResolvedToken]) -> SemanticTheme {
    let groups = groupedEntries(entries)
    let neutralEntries = pickNeutralEntries(from: groups)
    let neutralFamily = neutralEntries.first?.family ?? "grey"
    let baseEntries = groups["base"] ?? []
    let white = baseEntries.first(where: { $0.name == "white" })?.value ?? "FFFFFF"
    let black = baseEntries.first(where: { $0.name == "black" })?.value ?? "17181A"
    let accent = pickTone(
        pickAccentEntries(from: groups, neutralFamily: neutralFamily),
        targets: [600, 500, 400]
    ) ?? "0064EE"

    return SemanticTheme(
        background: pickTone(neutralEntries, targets: [100, 200]) ?? white,
        surface: white,
        surfaceAlt: white,
        border: pickTone(neutralEntries, targets: [300, 400]) ?? "D7D9DC",
        text: pickTone(neutralEntries, targets: [950, 900, 800]) ?? black,
        muted: pickTone(neutralEntries, targets: [700, 600, 500]) ?? black,
        accent: accent,
        accentContent: contrastColor(for: accent, light: white, dark: black)
    )
}

private let primitiveEntries = resolveTokens(from: primitivePairs)

let neutralPalette = buildNeutralPalette(from: primitiveEntries)

private func buildSemanticTheme(from entries: [ResolvedToken]) -> SemanticTheme {
    let groups = groupedEntries(entries)
    let surfaceEntries = groups["surface"]
    let textEntries = groups["text"]
    let iconEntries = groups["icon"]
    let borderEntries = groups["border"]
    let accentEntries = groups["accent"]
    let actionEntries = groups["action"]
    let statusEntries = groups["status"]
    let surface = pickOpaqueFamilyToken(surfaceEntries, index: 0) ?? neutralPalette.surface
    let background = pickOpaqueFamilyToken(surfaceEntries, index: 1) ?? surface
    let surfaceAlt = pickLastDistinctOpaqueToken(surfaceEntries, excluding: [surface, background])
        ?? pickOpaqueFamilyToken(surfaceEntries, index: 0)
        ?? neutralPalette.surfaceAlt
    let accent = pickOpaqueFamilyToken(accentEntries, index: 0)
        ?? pickOpaqueFamilyToken(actionEntries, index: 0)
        ?? pickOpaqueFamilyToken(statusEntries, index: 0)
        ?? neutralPalette.accent
    let accentContent = pickOpaqueFamilyToken(accentEntries, index: 1)
        ?? pickOpaqueFamilyToken(actionEntries, index: 1)
        ?? contrastColor(for: accent)

    return SemanticTheme(
        background: background,
        surface: surface,
        surfaceAlt: surfaceAlt,
        border: pickOpaqueFamilyToken(borderEntries, index: 1)
            ?? pickOpaqueFamilyToken(borderEntries, index: 0)
            ?? neutralPalette.border,
        text: pickOpaqueFamilyToken(textEntries, index: 0)
            ?? pickOpaqueFamilyToken(iconEntries, index: 0)
            ?? neutralPalette.text,
        muted: pickOpaqueFamilyToken(textEntries, index: 1)
            ?? pickOpaqueFamilyToken(iconEntries, index: 1)
            ?? neutralPalette.muted,
        accent: accent,
        accentContent: accentContent
    )
}

private func buildSemanticMode(
    id: String,
    name: String,
    tokens: [(name: String, value: String)]
) -> SemanticMode {
    let semanticTokenMap = Dictionary(uniqueKeysWithValues: tokens)
    let entries = resolveTokens(from: tokens, semanticTokens: semanticTokenMap)
    let groups = buildTokenGroups(from: entries)

    return SemanticMode(
        id: id,
        name: name,
        theme: buildSemanticTheme(from: entries),
        groups: groups
    )
}

let semanticModes: [SemanticMode] = [
    buildSemanticMode(id: "light", name: "Light", tokens: semanticLightPairs),
    buildSemanticMode(id: "dark", name: "Dark", tokens: semanticDarkPairs),
]

private let primitiveGroups = buildTokenGroups(from: primitiveEntries)

private let primitiveSection = DemoSection(
    id: "primitive",
    label: "Primitive",
    groups: primitiveGroups
)

let demoSections: [DemoSection] = [primitiveSection] + semanticModes.map { mode in
    DemoSection(
        id: "semantic-\(mode.id)",
        label: mode.name,
        groups: mode.groups
    )
}
