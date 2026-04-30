package com.worldcoin.nucleus.components.card

import androidx.compose.ui.graphics.Color

object NucleusCardConfigs {
    data class ColorWithAlpha(val color: Int, val alpha: Float = 1.0f)

    enum class Size(val value: String) {
        SMALL("Small"),
        MEDIUM("Medium"),
        LARGE("Large"),
        ;

        companion object {
            fun fromValue(value: String?): Size? = entries.find { it.value.equals(value, ignoreCase = true) }
        }
    }

    enum class AspectRatio(val value: String, val ratio: Float) {
        Landscape("Landscape", 16 / 11f),
        Portrait("Portrait", 3 / 4f),
        Square("Square", 1f),
        ;

        companion object {
            fun fromValue(value: String?): AspectRatio? = entries.find { it.value.equals(value, ignoreCase = true) }
        }
    }

    enum class TextPlacement(val value: String) {
        Overlay("Overlay"),
        Below("Below"),
        ;

        companion object {
            fun fromValue(value: String?): TextPlacement? = entries.find { it.value.equals(value, ignoreCase = true) }
        }
    }

    enum class Theme(
        val value: String,
        val primaryTextColor: ColorWithAlpha,
        val secondaryTextColor: ColorWithAlpha,
        val primaryContainerColor: ColorWithAlpha,
        val primaryContainerTextColor: ColorWithAlpha,
    ) {
        Light(
            value = "Light",
            primaryTextColor = ColorWithAlpha(0xFFFFFF),
            secondaryTextColor = ColorWithAlpha(0xFFFFFF, alpha = 0.6f),
            primaryContainerColor = ColorWithAlpha(0xFFFFFF),
            primaryContainerTextColor = ColorWithAlpha(0x181818),
        ),
        Dark(
            value = "Dark",
            primaryTextColor = ColorWithAlpha(0x181818),
            secondaryTextColor = ColorWithAlpha(0x717680),
            primaryContainerColor = ColorWithAlpha(0x181818),
            primaryContainerTextColor = ColorWithAlpha(0xFFFFFF),
        ),
        ;

        companion object {
            fun fromValue(value: String?): Theme? = entries.find { it.value.equals(value, ignoreCase = true) }
        }
    }
}

fun NucleusCardConfigs.Theme.toPrimaryTextColor(): Color =
    Color(primaryTextColor.color).copy(alpha = primaryTextColor.alpha)

fun NucleusCardConfigs.Theme.toSecondaryTextColor(): Color =
    Color(secondaryTextColor.color).copy(alpha = secondaryTextColor.alpha)

fun NucleusCardConfigs.Theme.toPrimaryContainerColor(): Color =
    Color(primaryContainerColor.color).copy(alpha = primaryContainerColor.alpha)

fun NucleusCardConfigs.Theme.toPrimaryContainerTextColor(): Color =
    Color(primaryContainerTextColor.color).copy(alpha = primaryContainerTextColor.alpha)
