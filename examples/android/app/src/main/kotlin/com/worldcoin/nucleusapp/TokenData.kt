package com.worldcoin.nucleusapp

import android.content.Context
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb

private const val GENERATED_TOKEN_DIR = "com/worldcoin/nucleus"
private const val PRIMITIVE_FILE = "$GENERATED_TOKEN_DIR/NucleusPrimitiveColors.kt"
private const val SEMANTIC_LIGHT_FILE = "$GENERATED_TOKEN_DIR/NucleusSemanticColorsLight.kt"
private const val SEMANTIC_DARK_FILE = "$GENERATED_TOKEN_DIR/NucleusSemanticColorsDark.kt"

private val tokenLineRegex = Regex("""^\s*val\s+([A-Za-z0-9_]+)\s*=\s*(.+)$""")
private val tokenReferenceRegex = Regex("""^Nucleus(PrimitiveColors|SemanticColors(?:Light|Dark))\.([A-Za-z0-9_]+)$""")
private val tokenColorRegex = Regex("""^Color\(0x([0-9A-Fa-f]{8})\)$""")

private data class ResolvedToken(
    val name: String,
    val value: String,
    val family: String,
    val tone: Int?,
)

private fun loadTokenPairs(context: Context, fileName: String): List<Pair<String, String>> =
    context.assets.open(fileName).bufferedReader().useLines { lines ->
        lines.mapNotNull { line ->
            tokenLineRegex.matchEntire(line)?.destructured?.let { (name, value) ->
                name to value.trim()
            }
        }.toList()
    }

private fun tokenFamilyKey(name: String): String {
    if (name == "white" || name == "black") {
        return "base"
    }

    val prefix = name.takeWhile { it.isLowerCase() }
    return prefix.ifEmpty { name }
}

private fun familyTitle(name: String): String =
    name.replaceFirstChar { if (it.isLowerCase()) it.titlecase() else it.toString() }

private fun tokenTone(name: String): Int? {
    val digits = name.takeLastWhile { it.isDigit() }
    return digits.toIntOrNull()
}

private fun referenceTokenKey(value: String): Pair<String, String>? {
    val match = tokenReferenceRegex.matchEntire(value) ?: return null
    val root = if (match.groupValues[1] == "PrimitiveColors") "primitive" else "semantic"
    return root to match.groupValues[2]
}

private fun colorLiteralValue(value: String): String? {
    val match = tokenColorRegex.matchEntire(value) ?: return null
    val argb = match.groupValues[1].uppercase()
    val alpha = argb.substring(0, 2)
    val rgb = argb.substring(2)
    return if (alpha == "FF") rgb else rgb + alpha
}

private fun resolveTokenEntries(
    tokens: List<Pair<String, String>>,
    primitiveTokens: Map<String, String>,
    semanticTokens: Map<String, String>? = null,
): List<ResolvedToken> =
    tokens.map { (name, rawValue) ->
        ResolvedToken(
            name = name,
            value = resolveColorValue(rawValue, primitiveTokens, semanticTokens),
            family = tokenFamilyKey(name),
            tone = tokenTone(name),
        )
    }

private fun resolveColorValue(
    value: String,
    primitiveTokens: Map<String, String>,
    semanticTokens: Map<String, String>? = null,
): String {
    colorLiteralValue(value)?.let { return it }

    val reference = referenceTokenKey(value) ?: return value.removePrefix("#").uppercase()
    val sourceTokens = if (reference.first == "primitive") primitiveTokens else semanticTokens
    val resolved = sourceTokens?.get(reference.second) ?: return value.removePrefix("#").uppercase()

    if (resolved == value) {
        return value.removePrefix("#").uppercase()
    }

    return resolveColorValue(resolved, primitiveTokens, semanticTokens)
}

private fun String.toComposeColor(): Color {
    val normalized = removePrefix("#").uppercase()
    val argbHex = when (normalized.length) {
        6 -> "FF$normalized"
        8 -> normalized.takeLast(2) + normalized.dropLast(2)
        else -> error("Unsupported color format: $this")
    }

    return Color(argbHex.toLong(radix = 16).toInt())
}

private fun groupEntriesByFamily(entries: List<ResolvedToken>): LinkedHashMap<String, MutableList<ResolvedToken>> {
    val groups = linkedMapOf<String, MutableList<ResolvedToken>>()

    entries.forEach { entry ->
        groups.getOrPut(entry.family) { mutableListOf() }.add(entry)
    }

    return groups
}

private fun isOpaque(hex: String): Boolean {
    val normalized = hex.removePrefix("#").uppercase()
    return normalized.length != 8 || normalized.endsWith("FF")
}

private fun pickOpaqueFamilyToken(entries: List<ResolvedToken>?, index: Int = 0): String? {
    val opaqueEntries = entries?.filter { isOpaque(it.value) }.orEmpty()
    if (opaqueEntries.isEmpty()) {
        return null
    }

    return opaqueEntries.getOrNull(index)?.value ?: opaqueEntries.first().value
}

private fun pickLastDistinctOpaqueToken(entries: List<ResolvedToken>?, excluded: List<String>): String? =
    entries
        ?.asReversed()
        ?.firstOrNull { entry -> isOpaque(entry.value) && entry.value !in excluded }
        ?.value

private fun pickTone(entries: List<ResolvedToken>?, targets: List<Int>): String? {
    val tonedEntries = entries?.filter { it.tone != null }.orEmpty()
    if (tonedEntries.isEmpty()) {
        return entries?.firstOrNull()?.value
    }

    targets.forEach { target ->
        tonedEntries.firstOrNull { it.tone == target }?.let { return it.value }
    }

    val firstTarget = targets.firstOrNull() ?: return tonedEntries.first().value
    return tonedEntries.minByOrNull { kotlin.math.abs((it.tone ?: firstTarget) - firstTarget) }?.value
}

private fun pickNeutralEntries(groups: Map<String, List<ResolvedToken>>): List<ResolvedToken> {
    val candidates = listOf("grey", "gray", "neutral", "stone", "slate")

    candidates.forEach { family ->
        val entries = groups[family]
        if (entries?.any { it.tone != null } == true) {
            return entries
        }
    }

    return groups.values
        .filter { entries -> entries.any { it.tone != null } }
        .maxByOrNull { it.size }
        .orEmpty()
}

private fun pickAccentEntries(
    groups: Map<String, List<ResolvedToken>>,
    neutralFamily: String,
): List<ResolvedToken> {
    val priorities = listOf("info", "accent", "brand", "blue")

    priorities.forEach { family ->
        groups[family]?.let { return it }
    }

    return groups.entries.firstOrNull { (family, _) ->
        family != "base" && family != neutralFamily
    }?.value.orEmpty()
}

private fun contrastColor(color: String, light: String = "FFFFFF", dark: String = "17181A"): String {
    val normalized = color.removePrefix("#").uppercase()
    val rgb = normalized.take(6)
    if (rgb.length != 6) {
        return light
    }

    val red = rgb.substring(0, 2).toInt(16)
    val green = rgb.substring(2, 4).toInt(16)
    val blue = rgb.substring(4, 6).toInt(16)
    val luminance = (0.2126 * red + 0.7152 * green + 0.0722 * blue) / 255

    return if (luminance > 0.6) dark else light
}

private fun buildTokenGroups(
    tokens: List<ResolvedToken>,
): List<TokenGroup> {
    val groupedTokens = linkedMapOf<String, MutableList<TokenSwatch>>()

    tokens.forEach { token ->
        val family = token.family
        groupedTokens.getOrPut(family) { mutableListOf() }
            .add(
                TokenSwatch(
                    id = token.name,
                    name = token.name,
                    hex = token.value,
                    color = token.value.toComposeColor(),
                ),
            )
    }

    return groupedTokens.map { (family, swatches) ->
        TokenGroup(
            id = family,
            name = familyTitle(family),
            tokens = swatches.toList(),
        )
    }
}

private fun buildNeutralTheme(entries: List<ResolvedToken>): TokenTheme {
    val groups = groupEntriesByFamily(entries)
    val neutralEntries = pickNeutralEntries(groups)
    val neutralFamily = neutralEntries.firstOrNull()?.family ?: "grey"
    val baseEntries = groups["base"].orEmpty()
    val white = baseEntries.firstOrNull { it.name == "white" }?.value ?: "FFFFFF"
    val black = baseEntries.firstOrNull { it.name == "black" }?.value ?: "17181A"
    val accent = pickTone(pickAccentEntries(groups, neutralFamily), listOf(600, 500, 400)) ?: "0064EE"

    return TokenTheme(
        background = (pickTone(neutralEntries, listOf(100, 200)) ?: white).toComposeColor(),
        surface = white.toComposeColor(),
        surfaceAlt = white.toComposeColor(),
        border = (pickTone(neutralEntries, listOf(300, 400)) ?: "D7D9DC").toComposeColor(),
        text = (pickTone(neutralEntries, listOf(950, 900, 800)) ?: black).toComposeColor(),
        muted = (pickTone(neutralEntries, listOf(700, 600, 500)) ?: black).toComposeColor(),
        accent = accent.toComposeColor(),
        accentContent = contrastColor(accent, white, black).toComposeColor(),
    )
}

private fun buildSemanticTheme(
    entries: List<ResolvedToken>,
    neutralTheme: TokenTheme,
): TokenTheme {
    val groups = groupEntriesByFamily(entries)
    val surfaceEntries = groups["surface"]
    val textEntries = groups["text"]
    val iconEntries = groups["icon"]
    val borderEntries = groups["border"]
    val accentEntries = groups["accent"]
    val actionEntries = groups["action"]
    val statusEntries = groups["status"]
    val surface = pickOpaqueFamilyToken(surfaceEntries, 0)?.toComposeColor() ?: neutralTheme.surface
    val background = pickOpaqueFamilyToken(surfaceEntries, 1)?.toComposeColor() ?: surface
    val surfaceAltHex =
        pickLastDistinctOpaqueToken(surfaceEntries, listOf(surface.toHexString(), background.toHexString()))
            ?: pickOpaqueFamilyToken(surfaceEntries, 0)
            ?: neutralTheme.surfaceAlt.toHexString()
    val accentHex =
        pickOpaqueFamilyToken(accentEntries, 0)
            ?: pickOpaqueFamilyToken(actionEntries, 0)
            ?: pickOpaqueFamilyToken(statusEntries, 0)
            ?: neutralTheme.accent.toHexString()
    val accentContentHex =
        pickOpaqueFamilyToken(accentEntries, 1)
            ?: pickOpaqueFamilyToken(actionEntries, 1)
            ?: contrastColor(accentHex)

    return TokenTheme(
        background = background,
        surface = surface,
        surfaceAlt = surfaceAltHex.toComposeColor(),
        border = (
            pickOpaqueFamilyToken(borderEntries, 1)
                ?: pickOpaqueFamilyToken(borderEntries, 0)
                ?: neutralTheme.border.toHexString()
            ).toComposeColor(),
        text = (
            pickOpaqueFamilyToken(textEntries, 0)
                ?: pickOpaqueFamilyToken(iconEntries, 0)
                ?: neutralTheme.text.toHexString()
            ).toComposeColor(),
        muted = (
            pickOpaqueFamilyToken(textEntries, 1)
                ?: pickOpaqueFamilyToken(iconEntries, 1)
                ?: neutralTheme.muted.toHexString()
            ).toComposeColor(),
        accent = accentHex.toComposeColor(),
        accentContent = accentContentHex.toComposeColor(),
    )
}

private fun buildSemanticMode(
    id: String,
    name: String,
    tokens: List<Pair<String, String>>,
    primitiveTokens: Map<String, String>,
    neutralTheme: TokenTheme,
): SemanticModeData {
    val semanticTokenMap = tokens.toMap()
    val entries = resolveTokenEntries(tokens, primitiveTokens, semanticTokenMap)
    val groups = buildTokenGroups(entries)

    return SemanticModeData(
        id = id,
        name = name,
        theme = buildSemanticTheme(entries, neutralTheme),
        groups = groups,
    )
}

fun Color.toHexString(): String {
    val argb = toArgb()
    val alpha = (argb ushr 24) and 0xFF
    val red = (argb ushr 16) and 0xFF
    val green = (argb ushr 8) and 0xFF
    val blue = argb and 0xFF
    return if (alpha == 0xFF) {
        String.format("%02X%02X%02X", red, green, blue)
    } else {
        String.format("%02X%02X%02X%02X", red, green, blue, alpha)
    }
}

fun loadTokenCatalog(context: Context): TokenCatalog {
    val primitivePairs = loadTokenPairs(context, PRIMITIVE_FILE)
    val semanticLightPairs = loadTokenPairs(context, SEMANTIC_LIGHT_FILE)
    val semanticDarkPairs = loadTokenPairs(context, SEMANTIC_DARK_FILE)
    val primitiveMap = primitivePairs.toMap()
    val primitiveEntries = resolveTokenEntries(primitivePairs, primitiveMap)
    val neutralTheme = buildNeutralTheme(primitiveEntries)

    val semanticModes = listOf(
        buildSemanticMode("light", "Light", semanticLightPairs, primitiveMap, neutralTheme),
        buildSemanticMode("dark", "Dark", semanticDarkPairs, primitiveMap, neutralTheme),
    )

    val appearanceThemes = semanticModes.map { mode ->
        AppearanceTheme(
            id = mode.id,
            name = mode.name,
            theme = mode.theme,
        )
    }

    val primitiveGroups = buildTokenGroups(primitiveEntries)

    val tokenSections = listOf(
        TokenSectionData(
            id = "primitive",
            label = "Primitive",
            groups = primitiveGroups,
        ),
    ) + semanticModes.map { mode ->
        TokenSectionData(
            id = "semantic-${mode.id}",
            label = mode.name,
            groups = mode.groups,
        )
    }

    return TokenCatalog(
        appearanceThemes = appearanceThemes,
        tokenSections = tokenSections,
    )
}
