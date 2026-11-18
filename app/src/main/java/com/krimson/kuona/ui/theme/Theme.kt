package com.krimson.kuona.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val KuonaColorScheme = darkColorScheme(
    primary = KuonaThreatRed,
    secondary = KuonaForeground,
    background = KuonaBlack,
    surface = KuonaBlack,
    onPrimary = KuonaBlack,
    onSecondary = KuonaBlack,
    onBackground = KuonaForeground,
    onSurface = KuonaForeground
)

@Composable
fun KuonaTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = KuonaColorScheme,
        typography = KuonaTypography,
        content = content
    )
}
