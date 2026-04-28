package com.worldcoin.nucleus

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Mirrors the SDUI v3 Card spec. Image and logo are caller-provided slots so this module
 * stays free of any image-loading dependency — pass an `Image(painter = ...)` from Coil,
 * Glide, or `painterResource(...)`.
 */
enum class CardSize { Small, Medium, Large }

enum class CardAspectRatio { Landscape, Square, Portrait }

enum class CardTextPlacement { Below, Overlay }

enum class CardTheme { Light, Dark }

data class CardStyle(
    val size: CardSize = CardSize.Medium,
    val aspectRatio: CardAspectRatio = CardAspectRatio.Landscape,
    val textPlacement: CardTextPlacement = CardTextPlacement.Below,
    val theme: CardTheme = CardTheme.Dark,
    val cornerRadius: Dp = 12.dp,
    val borderWidth: Dp = 0.dp,
    val borderColor: Color = Color.Transparent,
)

data class CardCta(
    val title: String,
    val onClick: () -> Unit,
)

@Composable
fun Card(
    title: String,
    imageContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    logoContent: (@Composable () -> Unit)? = null,
    style: CardStyle = CardStyle(),
    cta: CardCta? = null,
    onClick: (() -> Unit)? = null,
) {
    val shape = RoundedCornerShape(style.cornerRadius)
    val rootModifier = modifier
        .fillMaxWidth()
        .clip(shape)
        .let { if (style.borderWidth > 0.dp) it.border(style.borderWidth, style.borderColor, shape) else it }
        .let { if (onClick != null) it.clickable(onClick = onClick) else it }

    when (style.textPlacement) {
        CardTextPlacement.Overlay -> OverlayCard(rootModifier, style, title, subtitle, logoContent, cta, imageContent)
        CardTextPlacement.Below -> BelowCard(rootModifier, style, title, subtitle, logoContent, cta, imageContent)
    }
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
fun CardPreview() {
    Card(
        title = "Title",
        imageContent = { Text(text = "Image") },
        logoContent = { Text(text = "Logo") },
    )
}

@Composable
private fun OverlayCard(
    modifier: Modifier,
    style: CardStyle,
    title: String,
    subtitle: String?,
    logoContent: (@Composable () -> Unit)?,
    cta: CardCta?,
    imageContent: @Composable BoxScope.() -> Unit,
) {
    Box(modifier = modifier.aspectRatio(style.aspectRatio.ratio())) {
        Box(modifier = Modifier.fillMaxSize(), content = imageContent)
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            CardText(style, title, subtitle, logoContent)
            if (cta != null) CardCtaButton(cta = cta, theme = style.theme)
        }
    }
}

@Composable
private fun BelowCard(
    modifier: Modifier,
    style: CardStyle,
    title: String,
    subtitle: String?,
    logoContent: (@Composable () -> Unit)?,
    cta: CardCta?,
    imageContent: @Composable BoxScope.() -> Unit,
) {
    Column(modifier = modifier.background(style.theme.surface())) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(style.aspectRatio.ratio()),
            content = imageContent,
        )
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(modifier = Modifier.weight(1f)) {
                CardText(style, title, subtitle, logoContent)
            }
            if (cta != null) CardCtaButton(cta = cta, theme = style.theme)
        }
    }
}

@Composable
private fun CardText(
    style: CardStyle,
    title: String,
    subtitle: String?,
    logoContent: (@Composable () -> Unit)?,
) {
    val textColor = style.theme.onSurface()
    val (titleSize, subtitleSize) = when (style.size) {
        CardSize.Large -> 24.sp to 16.sp
        CardSize.Medium -> 18.sp to 14.sp
        CardSize.Small -> 15.sp to 13.sp
    }
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        if (logoContent != null) {
            Box(modifier = Modifier.size(24.dp)) { logoContent() }
        }
        Column {
            Text(
                text = title,
                color = textColor,
                fontSize = titleSize,
                fontWeight = FontWeight.SemiBold,
            )
            if (subtitle != null) {
                Text(
                    text = subtitle,
                    color = textColor.copy(alpha = 0.72f),
                    fontSize = subtitleSize,
                )
            }
        }
    }
}

@Composable
private fun CardCtaButton(cta: CardCta, theme: CardTheme) {
    val bg = theme.onSurface()
    val fg = theme.surface()
    Box(
        modifier = Modifier
            .clip(RoundedCornerShape(999.dp))
            .background(bg)
            .clickable(onClick = cta.onClick)
            .padding(horizontal = 16.dp, vertical = 8.dp),
    ) {
        Text(
            text = cta.title,
            color = fg,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        )
    }
}

private fun CardAspectRatio.ratio(): Float = when (this) {
    CardAspectRatio.Landscape -> 16f / 9f
    CardAspectRatio.Square -> 1f
    CardAspectRatio.Portrait -> 3f / 4f
}

private fun CardTheme.surface(): Color = when (this) {
    CardTheme.Light -> Color.White
    CardTheme.Dark -> Color(0xFF17181A)
}

private fun CardTheme.onSurface(): Color = when (this) {
    CardTheme.Light -> Color(0xFF17181A)
    CardTheme.Dark -> Color.White
}

object CardVariants {
    val LargeLight = CardStyle(CardSize.Large, CardAspectRatio.Landscape, CardTextPlacement.Overlay, CardTheme.Light, 16.dp, 0.dp, Color.Transparent)
    val LargeDark = CardStyle(CardSize.Large, CardAspectRatio.Landscape, CardTextPlacement.Overlay, CardTheme.Dark, 16.dp, 0.dp, Color.Transparent)
    val Medium = CardStyle(CardSize.Medium, CardAspectRatio.Landscape, CardTextPlacement.Below, CardTheme.Dark, 12.dp, 0.dp, Color.Transparent)
    val Small = CardStyle(CardSize.Small, CardAspectRatio.Landscape, CardTextPlacement.Below, CardTheme.Dark, 12.dp, 0.dp, Color.Transparent)
    val Tile = CardStyle(CardSize.Large, CardAspectRatio.Square, CardTextPlacement.Overlay, CardTheme.Dark, 24.dp, 1.dp, Color(0xFFF3F4F5))
    val Story = CardStyle(CardSize.Small, CardAspectRatio.Portrait, CardTextPlacement.Overlay, CardTheme.Dark, 12.dp, 1.dp, Color(0x0A181818))
}
