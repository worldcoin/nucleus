@file:OptIn(ExperimentalMaterialApi::class)

package com.worldcoin.nucleus.components.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Surface
import androidx.compose.material.Text
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

@Composable
internal fun NucleusCardWithNonFloatingInfoView(
    title: String,
    primaryImageUrl: String,
    titleStyle: TextStyle,
    subtitleStyle: TextStyle,
    modifier: Modifier = Modifier,
    description: String? = null,
    theme: NucleusCardConfigs.Theme = NucleusCardConfigs.Theme.Dark,
    aspectRatio: NucleusCardConfigs.AspectRatio = NucleusCardConfigs.AspectRatio.Landscape,
    cornerRadius: Dp = 16.dp,
    border: BorderStroke? = null,
    onClick: () -> Unit = {},
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed = interactionSource.collectIsPressedAsState().value
    val elevation by animateDpAsState(if (isPressed) 2.dp else 0.dp)
    val shape = RoundedCornerShape(cornerRadius)

    Surface(
        elevation = elevation,
        color = NucleusCardPalette.White,
        shape = shape,
        border = border,
        modifier = modifier
            .fillMaxWidth()
            .zIndex(elevation.toPx()),
        interactionSource = interactionSource,
        onClick = onClick,
    ) {
        Column(modifier = Modifier.padding(8.dp)) {
            AsyncImage(
                modifier = Modifier
                    .aspectRatio(aspectRatio.ratio)
                    .clip(shape)
                    .background(
                        color = NucleusCardPalette.Grey500,
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

                VerticalSpacer(2.dp)

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
