package com.worldcoin.nucleusapp

import androidx.compose.ui.graphics.Color

data class TokenTheme(
    val background: Color,
    val surface: Color,
    val surfaceAlt: Color,
    val border: Color,
    val text: Color,
    val muted: Color,
    val accent: Color,
    val accentContent: Color,
)

data class AppearanceTheme(
    val id: String,
    val name: String,
    val theme: TokenTheme,
)

data class TokenGroup(
    val id: String,
    val name: String,
    val tokens: List<TokenSwatch>,
)

data class TokenSwatch(
    val id: String,
    val name: String,
    val hex: String,
    val color: Color,
)

data class SemanticModeData(
    val id: String,
    val name: String,
    val theme: TokenTheme,
    val groups: List<TokenGroup>,
)

data class TokenSectionData(
    val id: String,
    val label: String,
    val groups: List<TokenGroup>,
)

data class TokenCatalog(
    val appearanceThemes: List<AppearanceTheme>,
    val tokenSections: List<TokenSectionData>,
)
