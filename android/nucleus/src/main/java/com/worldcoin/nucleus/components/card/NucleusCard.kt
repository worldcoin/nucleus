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
    ctaTextStyle: TextStyle = DefaultCtaTextStyle,
    size: NucleusCardConfigs.Size = NucleusCardConfigs.Size.MEDIUM,
    theme: NucleusCardConfigs.Theme = NucleusCardConfigs.Theme.Dark,
    aspectRatio: NucleusCardConfigs.AspectRatio = NucleusCardConfigs.AspectRatio.Landscape,
    textPlacement: NucleusCardConfigs.TextPlacement = NucleusCardConfigs.TextPlacement.Below,
    cornerRadius: Dp = 16.dp,
    border: BorderStroke? = null,
    onClick: () -> Unit = {},
    onCtaClick: () -> Unit = {},
) {
    when (textPlacement) {
        NucleusCardConfigs.TextPlacement.Overlay -> NucleusCardWithFloatingInfoView(
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
        NucleusCardConfigs.TextPlacement.Below -> NucleusCardWithNonFloatingInfoView(
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

private val previewTitleStyle = TextStyle(fontSize = 19.sp, lineHeight = 22.8.sp, fontWeight = FontWeight.SemiBold)
private val previewSubtitleStyle = TextStyle(fontSize = 19.sp, lineHeight = 24.7.sp, fontWeight = FontWeight.Normal)

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
        size = NucleusCardConfigs.Size.LARGE,
        theme = NucleusCardConfigs.Theme.Dark,
        aspectRatio = NucleusCardConfigs.AspectRatio.Landscape,
        textPlacement = NucleusCardConfigs.TextPlacement.Overlay,
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
        size = NucleusCardConfigs.Size.SMALL,
        theme = NucleusCardConfigs.Theme.Light,
        aspectRatio = NucleusCardConfigs.AspectRatio.Portrait,
        textPlacement = NucleusCardConfigs.TextPlacement.Overlay,
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
        size = NucleusCardConfigs.Size.SMALL,
        theme = NucleusCardConfigs.Theme.Light,
        aspectRatio = NucleusCardConfigs.AspectRatio.Square,
        textPlacement = NucleusCardConfigs.TextPlacement.Overlay,
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
        theme = NucleusCardConfigs.Theme.Dark,
        aspectRatio = NucleusCardConfigs.AspectRatio.Landscape,
        textPlacement = NucleusCardConfigs.TextPlacement.Below,
    )
}
