package com.worldcoin.nucleus.components.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

@Composable
internal fun Dp.toPx(): Float {
    val density = LocalDensity.current.density
    return this.value * density
}
