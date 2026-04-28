package com.worldcoin.nucleus

import androidx.compose.foundation.BorderStroke
import androidx.compose.runtime.Stable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.worldcoin.nucleus.card.CardAspectRatio
import com.worldcoin.nucleus.card.CardSize
import com.worldcoin.nucleus.card.CardTextPlacement
import com.worldcoin.nucleus.card.CardTheme

@Stable
data class CardStyle(
    val size: CardSize = CardSize.Medium,
    val aspectRatio: CardAspectRatio = CardAspectRatio.Landscape,
    val textPlacement: CardTextPlacement = CardTextPlacement.Below,
    val theme: CardTheme = CardTheme.Dark,
    val cornerRadius: Dp = 16.dp,
    val border: BorderStroke? = null,
    val titleTextStyle: TextStyle = TextStyle(fontSize = 16.sp, fontWeight = FontWeight.SemiBold),
    val subtitleTextStyle: TextStyle = TextStyle(fontSize = 14.sp),
)
