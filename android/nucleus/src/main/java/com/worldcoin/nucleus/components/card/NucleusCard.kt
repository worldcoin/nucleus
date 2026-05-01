package com.worldcoin.nucleus.components.card

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A clickable card displaying a primary image alongside a title, optional description, optional
 * logo, and optional call-to-action.
 *
 * The layout is selected by [textPlacement]: [NucleusCardTextPlacement.Overlay] floats the
 * info row over the image, while [NucleusCardTextPlacement.Below] stacks it underneath.
 * Width fills the parent and height is derived from [aspectRatio].
 *
 * @param title Primary text shown in the info row.
 * @param primaryImageUrl URL of the card's main image.
 * @param titleStyle Text style for [title]; the color is overridden by [theme].
 * @param subtitleStyle Text style for [description]; the color is overridden by [theme].
 * @param modifier Modifier applied to the card root.
 * @param description Optional secondary text shown below [title].
 * @param logoUrl Optional logo image shown next to the title. Only rendered in the overlay layout.
 * @param ctaTitle Optional call-to-action label. Only rendered in the overlay layout.
 * @param ctaTextStyle Text style for the CTA label.
 * @param size Controls inner padding of the info row.
 * @param theme Controls primary and secondary text colors.
 * @param aspectRatio Width-to-height ratio of the card.
 * @param textPlacement Whether the info row floats over the image or sits below it.
 * @param cornerRadius Corner radius of the card surface.
 * @param border Optional border stroke applied to the card surface.
 * @param onClick Invoked when the card is clicked.
 * @param onCtaClick Invoked when the CTA is clicked. Ignored when [ctaTitle] is null or when
 *   [textPlacement] is [NucleusCardTextPlacement.Below].
 */
@Composable
fun NucleusCard(
    title: String,
    primaryImageUrl: String,
    titleStyle: TextStyle,
    subtitleStyle: TextStyle,
    modifier: Modifier = Modifier,
    description: String? = null,
    logoUrl: String? = null,
    ctaTitle: String? = null,
    ctaTextStyle: TextStyle = TextStyle(
        fontSize = 15.sp,
        lineHeight = 18.sp,
        fontWeight = FontWeight.Medium,
    ),
    size: NucleusCardSize = NucleusCardSize.Medium,
    theme: NucleusCardTheme = NucleusCardTheme.Dark,
    aspectRatio: NucleusCardAspectRatio = NucleusCardAspectRatio.Landscape,
    textPlacement: NucleusCardTextPlacement = NucleusCardTextPlacement.Below,
    cornerRadius: Dp = 16.dp,
    border: BorderStroke? = null,
    onClick: () -> Unit = {},
    onCtaClick: () -> Unit = {},
) {
    when (textPlacement) {
        NucleusCardTextPlacement.Overlay -> NucleusCardWithFloatingInfoView(
            title = title,
            primaryImageUrl = primaryImageUrl,
            titleStyle = titleStyle,
            subtitleStyle = subtitleStyle,
            modifier = modifier,
            description = description,
            logoUrl = logoUrl,
            ctaTitle = ctaTitle,
            ctaTextStyle = ctaTextStyle,
            size = size,
            theme = theme,
            aspectRatio = aspectRatio,
            cornerRadius = cornerRadius,
            border = border,
            onClick = onClick,
            onCtaClick = onCtaClick,
        )

        NucleusCardTextPlacement.Below -> NucleusCardWithNonFloatingInfoView(
            title = title,
            primaryImageUrl = primaryImageUrl,
            titleStyle = titleStyle,
            subtitleStyle = subtitleStyle,
            modifier = modifier,
            description = description,
            theme = theme,
            aspectRatio = aspectRatio,
            cornerRadius = cornerRadius,
            border = border,
            onClick = onClick,
        )
    }
}

private val previewTitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 22.8.sp,
    fontWeight = FontWeight.SemiBold,
)
private val previewSubtitleStyle = TextStyle(
    fontSize = 19.sp,
    lineHeight = 24.7.sp,
    fontWeight = FontWeight.Normal,
)

@Preview
@Composable
private fun NucleusCardOverlayLandscapePreview() {
    NucleusCard(
        title = "Title goes here",
        primaryImageUrl = "",
        titleStyle = previewTitleStyle,
        subtitleStyle = previewSubtitleStyle,
        description = "Description goes here",
        ctaTitle = "Verify",
        size = NucleusCardSize.Large,
        theme = NucleusCardTheme.Dark,
        aspectRatio = NucleusCardAspectRatio.Landscape,
        textPlacement = NucleusCardTextPlacement.Overlay,
    )
}

@Preview
@Composable
private fun NucleusCardOverlayPortraitPreview() {
    NucleusCard(
        title = "Title goes here",
        primaryImageUrl = "",
        titleStyle = previewTitleStyle,
        subtitleStyle = previewSubtitleStyle,
        description = "Description goes here",
        ctaTitle = "Verify",
        size = NucleusCardSize.Small,
        theme = NucleusCardTheme.Light,
        aspectRatio = NucleusCardAspectRatio.Portrait,
        textPlacement = NucleusCardTextPlacement.Overlay,
    )
}

@Preview
@Composable
private fun NucleusCardOverlaySquareNoCtaPreview() {
    NucleusCard(
        title = "Title goes here",
        primaryImageUrl = "",
        titleStyle = previewTitleStyle,
        subtitleStyle = previewSubtitleStyle,
        description = "Description goes here",
        size = NucleusCardSize.Small,
        theme = NucleusCardTheme.Light,
        aspectRatio = NucleusCardAspectRatio.Square,
        textPlacement = NucleusCardTextPlacement.Overlay,
    )
}

@Preview(showBackground = true)
@Composable
private fun NucleusCardBelowPreview() {
    NucleusCard(
        title = "Title goes here",
        primaryImageUrl = "",
        titleStyle = previewTitleStyle,
        subtitleStyle = previewSubtitleStyle,
        description = "Description goes here",
        theme = NucleusCardTheme.Dark,
        aspectRatio = NucleusCardAspectRatio.Landscape,
        textPlacement = NucleusCardTextPlacement.Below,
    )
}
