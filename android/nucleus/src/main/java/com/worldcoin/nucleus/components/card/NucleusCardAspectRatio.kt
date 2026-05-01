package com.worldcoin.nucleus.components.card

/**
 * Width-to-height ratio applied to the card.
 *
 * @property ratio The numeric aspect ratio applied via `Modifier.aspectRatio`.
 */
enum class NucleusCardAspectRatio(val ratio: Float) {
    Landscape(ratio = 16 / 11f),
    Portrait(ratio = 3 / 4f),
    Square(ratio = 1f),
}
