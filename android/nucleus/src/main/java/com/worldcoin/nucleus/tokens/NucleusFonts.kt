// This file is auto-generated. Do not edit manually.

package com.worldcoin.nucleus.tokens

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.worldcoin.nucleus.R

/**
 * A Nucleus design token font.
 *
 * Each value carries enough information to construct a Compose `TextStyle`. The `fontFamily` is
 * wired to the bundled Nucleus font resource, so consumers never need to reference `R.font.*`
 * directly. See `NucleusFonts` for the available tokens and the repo `README.md#typography-tokens`
 * for usage guidance.
 */
data class NucleusFontStyle(
    val fontFamily: FontFamily,
    val fontSize: TextUnit,
    val fontWeight: FontWeight,
    val letterSpacing: TextUnit,
    val lineHeight: TextUnit,
)

object NucleusFonts {
    val d1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 56.sp,
        fontWeight = FontWeight(600),
        letterSpacing = (-0.02).em,
        lineHeight = (1).em,
    )
    val h1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 34.sp,
        fontWeight = FontWeight(550),
        letterSpacing = (-0.015).em,
        lineHeight = (1.2).em,
    )
    val h2 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 30.sp,
        fontWeight = FontWeight(550),
        letterSpacing = (-0.015).em,
        lineHeight = (1.2).em,
    )
    val h3 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 26.sp,
        fontWeight = FontWeight(550),
        letterSpacing = (-0.015).em,
        lineHeight = (1.2).em,
    )
    val h4 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 22.sp,
        fontWeight = FontWeight(550),
        letterSpacing = (-0.01).em,
        lineHeight = (1.2).em,
    )
    val h5 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 19.sp,
        fontWeight = FontWeight(550),
        letterSpacing = (-0.01).em,
        lineHeight = (1.2).em,
    )
    val s1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 17.sp,
        fontWeight = FontWeight(500),
        letterSpacing = (-0.01).em,
        lineHeight = (1.2).em,
    )
    val s2 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 15.sp,
        fontWeight = FontWeight(500),
        letterSpacing = (0).em,
        lineHeight = (1.2).em,
    )
    val s3 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 13.sp,
        fontWeight = FontWeight(500),
        letterSpacing = (0).em,
        lineHeight = (1.2).em,
    )
    val l1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 17.sp,
        fontWeight = FontWeight(600),
        letterSpacing = (0).em,
        lineHeight = (1.2).em,
    )
    val l2 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 15.sp,
        fontWeight = FontWeight(600),
        letterSpacing = (0).em,
        lineHeight = (1.2).em,
    )
    val l3 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 13.sp,
        fontWeight = FontWeight(600),
        letterSpacing = (0).em,
        lineHeight = (1.2).em,
    )
    val b1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 17.sp,
        fontWeight = FontWeight(350),
        letterSpacing = (0).em,
        lineHeight = (1.3).em,
    )
    val b2 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 15.sp,
        fontWeight = FontWeight(350),
        letterSpacing = (0).em,
        lineHeight = (1.3).em,
    )
    val b3 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 13.sp,
        fontWeight = FontWeight(350),
        letterSpacing = (0).em,
        lineHeight = (1.3).em,
    )
    val c1 = NucleusFontStyle(
        fontFamily = FontFamily(Font(R.font.world_pro_mvp)),
        fontSize = 11.sp,
        fontWeight = FontWeight(350),
        letterSpacing = (0.003).em,
        lineHeight = (1.4).em,
    )
}
