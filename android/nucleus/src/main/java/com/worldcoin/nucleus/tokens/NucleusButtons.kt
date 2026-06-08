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
            NucleusSemanticColorsLight.actionPrimary,
            NucleusSemanticColorsDark.actionPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionPrimaryContent,
            NucleusSemanticColorsDark.actionPrimaryContent,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val primary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionPrimary,
            NucleusSemanticColorsDark.actionPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionPrimaryContent,
            NucleusSemanticColorsDark.actionPrimaryContent,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val primary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionPrimary,
            NucleusSemanticColorsDark.actionPrimary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionPrimaryContent,
            NucleusSemanticColorsDark.actionPrimaryContent,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
    val secondary32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondary,
            NucleusSemanticColorsDark.actionSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondaryContent,
            NucleusSemanticColorsDark.actionSecondaryContent,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val secondary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondary,
            NucleusSemanticColorsDark.actionSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondaryContent,
            NucleusSemanticColorsDark.actionSecondaryContent,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val secondary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondary,
            NucleusSemanticColorsDark.actionSecondary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionSecondaryContent,
            NucleusSemanticColorsDark.actionSecondaryContent,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
    val tertiary32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiary,
            NucleusSemanticColorsDark.actionTertiary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiaryContent,
            NucleusSemanticColorsDark.actionTertiaryContent,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.borderDefault,
            NucleusSemanticColorsDark.borderDefault,
        ),
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val tertiary40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiary,
            NucleusSemanticColorsDark.actionTertiary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiaryContent,
            NucleusSemanticColorsDark.actionTertiaryContent,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.borderDefault,
            NucleusSemanticColorsDark.borderDefault,
        ),
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val tertiary48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiary,
            NucleusSemanticColorsDark.actionTertiary,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionTertiaryContent,
            NucleusSemanticColorsDark.actionTertiaryContent,
        ),
        border = NucleusButtonColor(
            NucleusSemanticColorsLight.borderDefault,
            NucleusSemanticColorsDark.borderDefault,
        ),
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
    val ghost32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhost,
            NucleusSemanticColorsDark.actionGhost,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhostContent,
            NucleusSemanticColorsDark.actionGhostContent,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val ghost40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhost,
            NucleusSemanticColorsDark.actionGhost,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhostContent,
            NucleusSemanticColorsDark.actionGhostContent,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val ghost48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhost,
            NucleusSemanticColorsDark.actionGhost,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionGhostContent,
            NucleusSemanticColorsDark.actionGhostContent,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
    val inverse32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.surfaceElevated,
            NucleusSemanticColorsDark.surfaceElevated,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.textPrimary,
            NucleusSemanticColorsDark.textPrimary,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val inverse40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.surfaceElevated,
            NucleusSemanticColorsDark.surfaceElevated,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.textPrimary,
            NucleusSemanticColorsDark.textPrimary,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val inverse48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.surfaceElevated,
            NucleusSemanticColorsDark.surfaceElevated,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.textPrimary,
            NucleusSemanticColorsDark.textPrimary,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
    val disabled32 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabled,
            NucleusSemanticColorsDark.actionDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabledContent,
            NucleusSemanticColorsDark.actionDisabledContent,
        ),
        border = null,
        height = 32.dp,
        cornerRadius = 16.dp,
        paddingHorizontal = 16.dp,
        paddingVertical = 6.dp,
        font = NucleusFonts.s3,
        pressedInset = 1.dp,
    )
    val disabled40 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabled,
            NucleusSemanticColorsDark.actionDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabledContent,
            NucleusSemanticColorsDark.actionDisabledContent,
        ),
        border = null,
        height = 40.dp,
        cornerRadius = 20.dp,
        paddingHorizontal = 20.dp,
        paddingVertical = 10.dp,
        font = NucleusFonts.s2,
        pressedInset = 1.dp,
    )
    val disabled48 = NucleusButtonStyle(
        background = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabled,
            NucleusSemanticColorsDark.actionDisabled,
        ),
        content = NucleusButtonColor(
            NucleusSemanticColorsLight.actionDisabledContent,
            NucleusSemanticColorsDark.actionDisabledContent,
        ),
        border = null,
        height = 48.dp,
        cornerRadius = 24.dp,
        paddingHorizontal = 24.dp,
        paddingVertical = 12.dp,
        font = NucleusFonts.s1,
        pressedInset = 1.dp,
    )
}
