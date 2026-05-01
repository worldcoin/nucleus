package com.worldcoin.nucleus.components.card

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.collectIsPressedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import coil3.compose.AsyncImage
import coil3.request.ImageRequest
import com.worldcoin.nucleus.components.utils.toPx
import com.worldcoin.nucleus.tokens.NucleusPrimitiveColors

@Composable
internal fun NucleusCardWithFloatingInfoView(
    modifier: Modifier = Modifier,
    title: String,
    primaryImageUrl: String,
    titleStyle: TextStyle,
    subtitleStyle: TextStyle,
    description: String?,
    logoUrl: String?,
    ctaTitle: String?,
    ctaTextStyle: TextStyle,
    size: NucleusCardSize,
    theme: NucleusCardTheme,
    aspectRatio: NucleusCardAspectRatio,
    cornerRadius: Dp,
    border: BorderStroke?,
    onClick: () -> Unit,
    onCtaClick: () -> Unit,
) {
    val interactionSource = remember { MutableInteractionSource() }
    val isPressed by interactionSource.collectIsPressedAsState()
    val elevation by animateDpAsState(targetValue = if (isPressed) 4.dp else 0.dp)
    val shape = RoundedCornerShape(size = cornerRadius)

    Surface(
        shape = shape,
        shadowElevation = elevation,
        border = border,
        color = NucleusPrimitiveColors.grey500,
        modifier = modifier
            .fillMaxWidth()
            .aspectRatio(aspectRatio.ratio)
            .zIndex(elevation.toPx()),
        interactionSource = interactionSource,
        onClick = onClick,
    ) {
        Box {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest
                    .Builder(context = LocalContext.current)
                    .data(primaryImageUrl)
                    .build(),
                contentDescription = title,
                contentScale = ContentScale.Crop,
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(all = if (size === NucleusCardSize.Small) 16.dp else 20.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                logoUrl?.let {
                    AsyncImage(
                        modifier = Modifier
                            .size(40.dp)
                            .clip(shape = RoundedCornerShape(size = 12.dp))
                            .background(color = NucleusPrimitiveColors.grey700),
                        model = ImageRequest
                            .Builder(context = LocalContext.current)
                            .data(it)
                            .build(),
                        contentDescription = title,
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                }
                Column(modifier = Modifier.weight(1f)) {
                    Text(
                        text = title,
                        style = titleStyle.copy(color = theme.toPrimaryTextColor()),
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis,
                    )
                    description?.let {
                        Text(
                            text = it,
                            style = subtitleStyle.copy(color = theme.toSecondaryTextColor()),
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            modifier = Modifier.padding(top = if (ctaTitle == null) 8.dp else 2.dp),
                        )
                    }
                }
                ctaTitle?.let {
                    Text(
                        modifier = Modifier
                            .padding(start = 8.dp)
                            .clip(CircleShape)
                            .background(
                                color = theme.toPrimaryContainerColor(),
                                shape = CircleShape,
                            )
                            .clickable { onCtaClick() }
                            .padding(horizontal = 16.dp, vertical = 8.dp),
                        text = it,
                        textAlign = TextAlign.Center,
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = ctaTextStyle.copy(color = theme.toPrimaryContainerTextColor()),
                    )
                }
            }
        }
    }
}
