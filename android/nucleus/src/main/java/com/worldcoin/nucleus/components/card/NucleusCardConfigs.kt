package com.worldcoin.nucleus.components.card

import androidx.compose.ui.graphics.Color

/** Configuration types used to parameterize [NucleusCard]. */
object NucleusCardConfigs {
    /**
     * An sRGB color packed as `0xRRGGBB` together with an opacity.
     *
     * @property color 24-bit RGB value (e.g. `0xFFFFFF` for white).
     * @property alpha Opacity in the range `[0f, 1f]`, where `0f` is fully transparent and `1f`
     *   is fully opaque.
     */
    data class ColorWithAlpha(val color: Int, val alpha: Float = 1.0f)

    /** Controls the inner padding of the card's info row. */
    enum class Size {
        SMALL,
        MEDIUM,
        LARGE,
    }

    /**
     * Width-to-height ratio applied to the card.
     *
     * @property ratio The numeric aspect ratio applied via `Modifier.aspectRatio`.
     */
    enum class AspectRatio(val ratio: Float) {
        Landscape(ratio = 16 / 11f),
        Portrait(ratio = 3 / 4f),
        Square(ratio = 1f),
    }

    /** Where the card's info row is placed relative to the primary image. */
    enum class TextPlacement {
        /** Floats the info row over the image. */
        Overlay,

        /** Stacks the info row underneath the image. */
        Below,
    }

    /**
     * Color palette applied to text and container surfaces inside the card.
     *
     * @property primaryTextColor Color used for the title.
     * @property secondaryTextColor Color used for the description.
     * @property primaryContainerColor Background color of the CTA container.
     * @property primaryContainerTextColor Foreground color of the CTA container.
     */
    enum class Theme(
        val primaryTextColor: ColorWithAlpha,
        val secondaryTextColor: ColorWithAlpha,
        val primaryContainerColor: ColorWithAlpha,
        val primaryContainerTextColor: ColorWithAlpha,
    ) {
        Light(
            primaryTextColor = ColorWithAlpha(0xFFFFFF),
            secondaryTextColor = ColorWithAlpha(0xFFFFFF, alpha = 0.6f),
            primaryContainerColor = ColorWithAlpha(0xFFFFFF),
            primaryContainerTextColor = ColorWithAlpha(0x181818),
        ),
        Dark(
            primaryTextColor = ColorWithAlpha(0x181818),
            secondaryTextColor = ColorWithAlpha(0x717680),
            primaryContainerColor = ColorWithAlpha(0x181818),
            primaryContainerTextColor = ColorWithAlpha(0xFFFFFF),
        ),
    }
}

/**
 * Resolves [NucleusCardConfigs.Theme.primaryTextColor] to a Compose [Color], applying its alpha.
 *
 * @return The primary text color as a Compose [Color].
 */
fun NucleusCardConfigs.Theme.toPrimaryTextColor(): Color =
    Color(primaryTextColor.color).copy(alpha = primaryTextColor.alpha)

/**
 * Resolves [NucleusCardConfigs.Theme.secondaryTextColor] to a Compose [Color], applying its alpha.
 *
 * @return The secondary text color as a Compose [Color].
 */
fun NucleusCardConfigs.Theme.toSecondaryTextColor(): Color =
    Color(secondaryTextColor.color).copy(alpha = secondaryTextColor.alpha)

/**
 * Resolves [NucleusCardConfigs.Theme.primaryContainerColor] to a Compose [Color], applying its
 * alpha.
 *
 * @return The primary container background color as a Compose [Color].
 */
fun NucleusCardConfigs.Theme.toPrimaryContainerColor(): Color =
    Color(primaryContainerColor.color).copy(alpha = primaryContainerColor.alpha)

/**
 * Resolves [NucleusCardConfigs.Theme.primaryContainerTextColor] to a Compose [Color], applying its
 * alpha.
 *
 * @return The primary container foreground color as a Compose [Color].
 */
fun NucleusCardConfigs.Theme.toPrimaryContainerTextColor(): Color =
    Color(primaryContainerTextColor.color).copy(alpha = primaryContainerTextColor.alpha)
