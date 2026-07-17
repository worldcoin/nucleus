// This file is auto-generated. Do not edit manually.

package com.worldcoin.nucleus.tokens

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A theme-aware color pair for a button token.
 *
 * Holds the light and dark semantic colors for a single button slot; `color(isDark)`
 * resolves to the value for the active theme.
 */
data class NucleusButtonColor(val light: Color, val dark: Color) {
    fun color(isDark: Boolean): Color = if (isDark) dark else light
}

/**
 * A Nucleus button style token.
 *
 * Carries the colors, geometry, and label font for a button variant × size. Colors are
 * theme-aware `NucleusButtonColor` pairs; `pressedInset` is the inset the client applies for
 * the pressed state. See `NucleusButtons` for the available tokens.
 */
data class NucleusButtonStyle(
    val background: NucleusButtonColor,
    val content: NucleusButtonColor,
    val border: NucleusButtonColor?,
    val height: Dp,
    val cornerRadius: Dp,
    val paddingHorizontal: Dp,
    val paddingVertical: Dp,
    val font: NucleusFontStyle,
    val pressedInset: Dp,
)

object NucleusButtons {
    val primary32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundPrimary,
            NucleusSemanticColorsDark.backgroundPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val primary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundPrimary,
            NucleusSemanticColorsDark.backgroundPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val primary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundPrimary,
            NucleusSemanticColorsDark.backgroundPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
    val secondary32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundSecondary,
            NucleusSemanticColorsDark.backgroundSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundSecondary,
            NucleusSemanticColorsDark.foregroundSecondary,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val secondary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundSecondary,
            NucleusSemanticColorsDark.backgroundSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundSecondary,
            NucleusSemanticColorsDark.foregroundSecondary,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val secondary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundSecondary,
            NucleusSemanticColorsDark.backgroundSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundSecondary,
            NucleusSemanticColorsDark.foregroundSecondary,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
    val tertiary32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.strokeSecondary,
            NucleusSemanticColorsDark.strokeSecondary,
        ),
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val tertiary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.strokeSecondary,
            NucleusSemanticColorsDark.strokeSecondary,
        ),
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val tertiary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.strokeSecondary,
            NucleusSemanticColorsDark.strokeSecondary,
        ),
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
    val inverse32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val inverse40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val inverse48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundInverse,
            NucleusSemanticColorsDark.backgroundInverse,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
    val ghost32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundTransparent,
            NucleusSemanticColorsDark.backgroundTransparent,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val ghost40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundTransparent,
            NucleusSemanticColorsDark.backgroundTransparent,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val ghost48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundTransparent,
            NucleusSemanticColorsDark.backgroundTransparent,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundPrimary,
            NucleusSemanticColorsDark.foregroundPrimary,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
    val disabled32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundDisabled,
            NucleusSemanticColorsDark.backgroundDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundDisabled,
            NucleusSemanticColorsDark.foregroundDisabled,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 14.dp,
        paddingVertical = 8.dp,
        font = NucleusFonts.l3,
        pressedInset = 1.dp,
    )
    val disabled40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundDisabled,
            NucleusSemanticColorsDark.backgroundDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundDisabled,
            NucleusSemanticColorsDark.foregroundDisabled,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 11.dp,
        font = NucleusFonts.l2,
        pressedInset = 1.dp,
    )
    val disabled48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.backgroundDisabled,
            NucleusSemanticColorsDark.backgroundDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.foregroundDisabled,
            NucleusSemanticColorsDark.foregroundDisabled,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 14.dp,
        font = NucleusFonts.l1,
        pressedInset = 1.dp,
    )
}
