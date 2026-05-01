package com.worldcoin.nucleus.components.card

import androidx.compose.ui.graphics.Color

/**
 * An sRGB color packed as `0xRRGGBB` together with an opacity.
 *
 * @property color 24-bit RGB value (e.g. `0xFFFFFF` for white).
 * @property alpha Opacity in the range `[0f, 1f]`, where `0f` is fully transparent and `1f` is
 *   fully opaque.
 */
data class NucleusCardColor(
    val color: Int,
    val alpha: Float = 1.0f,
)

/**
 * Color palette applied to text and container surfaces inside the card.
 *
 * @property primaryTextColor Color used for the title.
 * @property secondaryTextColor Color used for the description.
 * @property primaryContainerColor Background color of the CTA container.
 * @property primaryContainerTextColor Foreground color of the CTA container.
 */
enum class NucleusCardTheme(
    val primaryTextColor: NucleusCardColor,
    val secondaryTextColor: NucleusCardColor,
    val primaryContainerColor: NucleusCardColor,
    val primaryContainerTextColor: NucleusCardColor,
) {
    Light(
        primaryTextColor = NucleusCardColor(0xFFFFFF),
        secondaryTextColor = NucleusCardColor(0xFFFFFF, alpha = 0.6f),
        primaryContainerColor = NucleusCardColor(0xFFFFFF),
        primaryContainerTextColor = NucleusCardColor(0x181818),
    ),
    Dark(
        primaryTextColor = NucleusCardColor(0x181818),
        secondaryTextColor = NucleusCardColor(0x717680),
        primaryContainerColor = NucleusCardColor(0x181818),
        primaryContainerTextColor = NucleusCardColor(0xFFFFFF),
    ),
}

/**
 * Resolves [NucleusCardTheme.primaryTextColor] to a Compose [Color], applying its alpha.
 *
 * @return The primary text color as a Compose [Color].
 */
fun NucleusCardTheme.toPrimaryTextColor(): Color =
    Color(primaryTextColor.color).copy(alpha = primaryTextColor.alpha)

/**
 * Resolves [NucleusCardTheme.secondaryTextColor] to a Compose [Color], applying its alpha.
 *
 * @return The secondary text color as a Compose [Color].
 */
fun NucleusCardTheme.toSecondaryTextColor(): Color =
    Color(secondaryTextColor.color).copy(alpha = secondaryTextColor.alpha)

/**
 * Resolves [NucleusCardTheme.primaryContainerColor] to a Compose [Color], applying its alpha.
 *
 * @return The primary container background color as a Compose [Color].
 */
fun NucleusCardTheme.toPrimaryContainerColor(): Color =
    Color(primaryContainerColor.color).copy(alpha = primaryContainerColor.alpha)

/**
 * Resolves [NucleusCardTheme.primaryContainerTextColor] to a Compose [Color], applying its alpha.
 *
 * @return The primary container foreground color as a Compose [Color].
 */
fun NucleusCardTheme.toPrimaryContainerTextColor(): Color =
    Color(primaryContainerTextColor.color).copy(alpha = primaryContainerTextColor.alpha)
