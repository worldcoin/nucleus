package com.worldcoin.nucleus.components.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import com.worldcoin.nucleus.components.utils.toPx
import com.worldcoin.nucleus.tokens.NucleusPrimitiveColors

@Composable
internal fun NucleusCardWithNonFloatingInfoView(
    title: String,
    primaryImageUrl: String,
    titleStyle: TextStyle,
    subtitleStyle: TextStyle,
    modifier: Modifier = Modifier,
    description: String?,
    theme: NucleusCardTheme,
    aspectRatio: NucleusCardAspectRatio,
    cornerRadius: Dp,
    border: BorderStroke?,
    onClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val elevation by animateDpAsState(targetValue = if (isPressed) 2.dp else 0.dp)
    val shape = RoundedCornerShape(size = cornerRadius)

    Surface(
        shadowElevation = elevation,
        color = NucleusPrimitiveColors.white,
        shape = shape,
        border = border,
        modifier = modifier
            .fillMaxWidth()
            .zIndex(elevation.toPx()),
        interactionSource = interactionSource,
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(all = 8.dp)) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(aspectRatio.ratio)
                    .clip(shape)
                    .background(
                        color = NucleusPrimitiveColors.grey500,
                        shape = shape,
                    ),
                model = primaryImageUrl,
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
            Column(
                modifier = Modifier.padding(top = 12.dp),
            ) {
                Text(
                    text = title,
                    style = titleStyle.copy(
                        color = theme.toPrimaryContainerColor(),
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(2.dp))
                description?.let {
                    Text(
                        text = it,
                        style = subtitleStyle.copy(
                            color = theme.toSecondaryTextColor(),
                        ),
                    )
                }
            }
        }
    }
}
