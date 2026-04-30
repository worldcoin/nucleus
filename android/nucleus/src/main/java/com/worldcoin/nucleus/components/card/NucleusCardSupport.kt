package com.worldcoin.nucleus.components.card

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

internal object NucleusCardPalette {
    // TODO: use tokens eventually after moving tokens into platform source sets and remove this
    val Grey500 = Color(0xFFB8B8B8)
    val Grey700 = Color(0xFF7D7D7D)
    val White = Color(0xFFFFFFFF)
}

internal val DefaultCtaTextStyle = TextStyle(fontSize = 15.sp, lineHeight = 18.sp, fontWeight = FontWeight.Medium)

@Composable
internal fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}

@Composable
internal fun VerticalSpacer(distance: Dp) {
    Spacer(modifier = Modifier.height(distance))
}

@Composable
internal fun HorizontalSpacer(distance: Dp) {
    Spacer(modifier = Modifier.width(distance))
}

@Composable
internal fun NucleusCardCtaView(
    title: String,
    theme: NucleusCardConfigs.Theme,
    textStyle: TextStyle,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        modifier = modifier
            .clip(CircleShape)
            .background(
                color = theme.toPrimaryContainerColor(),
                shape = CircleShape,
            )
            .clickable { onClick() }
            .padding(horizontal = 16.dp, vertical = 8.dp),
        text = title,
        textAlign = TextAlign.Center,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = textStyle.copy(color = theme.toPrimaryContainerTextColor()),
    )
}
