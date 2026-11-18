package com.krimson.kuona.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Using sans-serif-condensed as system fallback for Barlow Condensed
val KuonaFontFamily = FontFamily.SansSerif

val KuonaTypography = Typography(
    displayLarge = TextStyle(
        fontFamily = KuonaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 64.sp,
        letterSpacing = 0.02.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = KuonaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 24.sp,
        letterSpacing = 0.08.sp
    ),
    titleLarge = TextStyle(
        fontFamily = KuonaFontFamily,
        fontWeight = FontWeight.SemiBold,
        fontSize = 20.sp,
        letterSpacing = 0.06.sp
    ),
    bodyLarge = TextStyle(
        fontFamily = KuonaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 16.sp,
        letterSpacing = 0.04.sp
    ),
    labelSmall = TextStyle(
        fontFamily = KuonaFontFamily,
        fontWeight = FontWeight.Normal,
        fontSize = 12.sp,
        letterSpacing = 0.1.sp
    )
)
