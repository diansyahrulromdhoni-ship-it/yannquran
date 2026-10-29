package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val YannQuranDarkColorScheme = darkColorScheme(
    primary = Gold,
    onPrimary = Forest,
    primaryContainer = Emerald,
    onPrimaryContainer = GoldLight,
    secondary = GoldLight,
    onSecondary = Forest,
    secondaryContainer = SurfaceDarkCard,
    onSecondaryContainer = WarmWhite,
    tertiary = GoldAmber,
    onTertiary = ForestDark,
    background = Forest,
    onBackground = WarmWhite,
    surface = SurfaceDark,
    onSurface = WarmWhite,
    surfaceVariant = SurfaceDarkCard,
    onSurfaceVariant = TextSecondaryDark,
    outline = BorderGoldMuted,
    outlineVariant = Color(0x22FFFFFF)
)

private val YannQuranLightColorScheme = lightColorScheme(
    primary = Emerald,
    onPrimary = WarmWhite,
    primaryContainer = SurfaceSand,
    onPrimaryContainer = Emerald,
    secondary = GoldDark,
    onSecondary = WarmWhite,
    secondaryContainer = Color(0xFFE6EFEA),
    onSecondaryContainer = Forest,
    tertiary = Gold,
    onTertiary = Forest,
    background = WarmWhite,
    onBackground = Forest,
    surface = Color.White,
    onSurface = Forest,
    surfaceVariant = SurfaceSand,
    onSurfaceVariant = TextSecondaryLight,
    outline = BorderGoldMuted,
    outlineVariant = Color(0x1F064E3B)
)

@Composable
fun YannQuranTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    // We intentionally prioritize Yann Quran's Emerald & Gold identity
    // across both light and dark modes rather than system wallpaper colors
    val colorScheme = if (darkTheme) YannQuranDarkColorScheme else YannQuranLightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
