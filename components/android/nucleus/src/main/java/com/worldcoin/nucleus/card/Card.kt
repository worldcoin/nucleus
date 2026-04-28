package com.worldcoin.nucleus.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.worldcoin.nucleus.CardCta
import com.worldcoin.nucleus.CardEvent
import com.worldcoin.nucleus.CardStyle

@Composable
fun Card(
    title: String,
    imageContent: @Composable BoxScope.() -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    logoContent: (@Composable BoxScope.() -> Unit)? = null,
    cta: CardCta? = null,
    style: CardStyle = CardStyle(),
    onEvent: (CardEvent) -> Unit = {},
) {
    when (style.textPlacement) {
        CardTextPlacement.Overlay -> FloatingInfoCard(
            title = title,
            description = description,
            imageContent = imageContent,
            logoContent = logoContent,
            cta = cta,
            style = style,
            onEvent = onEvent,
            modifier = modifier,
        )

        CardTextPlacement.Below -> NonFloatingInfoCard(
            title = title,
            description = description,
            imageContent = imageContent,
            style = style,
            onEvent = onEvent,
            modifier = modifier,
        )
    }
}

@Composable
private fun FloatingInfoCard(
    title: String,
    description: String?,
    imageContent: @Composable BoxScope.() -> Unit,
    logoContent: (@Composable BoxScope.() -> Unit)?,
    cta: CardCta?,
    style: CardStyle,
    onEvent: (CardEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 4.dp else 0.dp)
    val shape = RoundedCornerShape(style.cornerRadius)

    Box(
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(style.aspectRatio.ratio)
            .zIndex(elevation.value)
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .then(style.border.toModifier(shape))
            .background(CardColors.imagePlaceholder)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onEvent(CardEvent.Tap)
            },
    ) {
        Box(modifier = Modifier.fillMaxSize(), content = imageContent)

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.BottomCenter)
                .padding(if (style.size == CardSize.Small) 16.dp else 20.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            if (logoContent != null) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(CardColors.logoPlaceholder),
                    content = logoContent,
                )

                Spacer(modifier = Modifier.size(width = 12.dp, height = 1.dp))
            }

            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = title,
                    style = style.titleTextStyle.copy(color = style.theme.primaryTextColor),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                if (description != null) {
                    Text(
                        text = description,
                        style = style.subtitleTextStyle.copy(color = style.theme.secondaryTextColor),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                        modifier = Modifier.padding(top = if (cta == null) 8.dp else 2.dp),
                    )
                }
            }

            if (cta != null) {
                CardCtaButton(
                    cta = cta,
                    theme = style.theme,
                    onEvent = onEvent,
                    modifier = Modifier.padding(start = 8.dp),
                )
            }
        }
    }
}

@Composable
private fun NonFloatingInfoCard(
    title: String,
    description: String?,
    imageContent: @Composable BoxScope.() -> Unit,
    style: CardStyle,
    onEvent: (CardEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(if (isPressed) 2.dp else 0.dp)
    val shape = RoundedCornerShape(style.cornerRadius)

    Column(
        modifier = modifier
            .fillMaxWidth()
            .zIndex(elevation.value)
            .shadow(elevation = elevation, shape = shape)
            .clip(shape)
            .then(style.border.toModifier(shape))
            .background(Color.White)
            .clickable(
                interactionSource = interactionSource,
                indication = null,
            ) {
                onEvent(CardEvent.Tap)
            }
            .padding(8.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(style.aspectRatio.ratio)
                .clip(shape)
                .background(CardColors.imagePlaceholder),
            content = imageContent,
        )

        Column(
            modifier = Modifier.padding(top = 12.dp),
        ) {
            Text(
                text = title,
                style = style.titleTextStyle.copy(color = style.theme.primaryContainerColor),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )

            if (description != null) {
                Text(
                    text = description,
                    style = style.subtitleTextStyle.copy(color = style.theme.secondaryTextColor),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                    modifier = Modifier.padding(top = 2.dp),
                )
            }
        }
    }
}

@Composable
private fun CardCtaButton(
    cta: CardCta,
    theme: CardTheme,
    onEvent: (CardEvent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .clip(CircleShape)
            .background(
                color = theme.primaryContainerColor,
                shape = CircleShape,
            )
            .clickable { onEvent(CardEvent.CtaTap(cta)) }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = cta.title,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = LocalTextStyle.current.copy(
            color = theme.primaryContainerTextColor,
            fontSize = 14.sp,
            fontWeight = FontWeight.SemiBold,
        ),
    )
}

private fun BorderStroke?.toModifier(shape: RoundedCornerShape): Modifier =
    if (this == null) {
        Modifier
    } else {
        Modifier.border(this, shape)
    }

private val CardTheme.primaryTextColor: Color
    get() = when (this) {
        CardTheme.Light -> Color.White
        CardTheme.Dark -> CardColors.grey950
    }

private val CardTheme.secondaryTextColor: Color
    get() = when (this) {
        CardTheme.Light -> Color.White.copy(alpha = 0.6f)
        CardTheme.Dark -> CardColors.grey700
    }

private val CardTheme.primaryContainerColor: Color
    get() = when (this) {
        CardTheme.Light -> Color.White
        CardTheme.Dark -> CardColors.grey950
    }

private val CardTheme.primaryContainerTextColor: Color
    get() = when (this) {
        CardTheme.Light -> CardColors.grey950
        CardTheme.Dark -> Color.White
    }

private object CardColors {
    val grey500 = Color(0xFF9FA2A5)
    val grey700 = Color(0xFF747679)
    val grey950 = Color(0xFF17181A)
    val imagePlaceholder = grey500
    val logoPlaceholder = grey700
}

object CardDefaults {
    val LargeOverlayLight = CardStyle(
        size = CardSize.Large,
        aspectRatio = CardAspectRatio.Landscape,
        textPlacement = CardTextPlacement.Overlay,
        theme = CardTheme.Light,
    )
    val LargeOverlayDark = CardStyle(
        size = CardSize.Large,
        aspectRatio = CardAspectRatio.Landscape,
        textPlacement = CardTextPlacement.Overlay,
        theme = CardTheme.Dark,
    )
    val MediumBelow = CardStyle(
        size = CardSize.Medium,
        aspectRatio = CardAspectRatio.Landscape,
        textPlacement = CardTextPlacement.Below,
        theme = CardTheme.Dark,
    )
    val SmallBelow = CardStyle(
        size = CardSize.Small,
        aspectRatio = CardAspectRatio.Landscape,
        textPlacement = CardTextPlacement.Below,
        theme = CardTheme.Dark,
    )
    val Tile = CardStyle(
        size = CardSize.Large,
        aspectRatio = CardAspectRatio.Square,
        textPlacement = CardTextPlacement.Overlay,
        theme = CardTheme.Dark,
        cornerRadius = 24.dp,
        border = BorderStroke(1.dp, Color(0xFFF3F4F5)),
    )
    val Story = CardStyle(
        size = CardSize.Small,
        aspectRatio = CardAspectRatio.Portrait,
        textPlacement = CardTextPlacement.Overlay,
        theme = CardTheme.Dark,
        border = BorderStroke(1.dp, Color(0x0A181818)),
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun OverlayCardPreview() {
    Card(
        title = "Title goes here",
        description = "Description goes here",
        imageContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD7D9DC)),
            )
        },
        logoContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White),
            )
        },
        cta = CardCta(title = "Verify"),
        style = CardDefaults.LargeOverlayLight,
    )
}

@Preview(showBackground = true, backgroundColor = 0xFFFFFFFF)
@Composable
private fun BelowCardPreview() {
    Card(
        title = "Title goes here",
        description = "Description goes here",
        imageContent = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFFD7D9DC)),
            )
        },
        style = CardDefaults.SmallBelow,
    )
}
