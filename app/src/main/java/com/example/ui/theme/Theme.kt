package com.example.ui.theme

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = AuraPrimaryDark,
    onPrimary = AuraOnPrimaryDark,
    primaryContainer = AuraPrimaryContainerDark,
    onPrimaryContainer = AuraOnPrimaryContainerDark,
    secondary = AuraSecondaryDark,
    onSecondary = AuraOnSecondaryDark,
    secondaryContainer = AuraSecondaryContainerDark,
    onSecondaryContainer = AuraOnSecondaryContainerDark,
    tertiary = AuraTertiaryDark,
    onTertiary = AuraOnTertiaryDark,
    tertiaryContainer = AuraTertiaryContainerDark,
    onTertiaryContainer = AuraOnTertiaryContainerDark,
    background = AuraBackgroundDark,
    onBackground = AuraOnBackgroundDark,
    surface = AuraSurfaceDark,
    onSurface = AuraOnSurfaceDark,
    surfaceVariant = AuraSurfaceVariantDark,
    onSurfaceVariant = AuraOnSurfaceVariantDark,
    outline = AuraOutlineDark,
    outlineVariant = AuraOutlineVariantDark
)

private val LightColorScheme = lightColorScheme(
    primary = AuraPrimaryLight,
    onPrimary = AuraOnPrimaryLight,
    primaryContainer = AuraPrimaryContainerLight,
    onPrimaryContainer = AuraOnPrimaryContainerLight,
    secondary = AuraSecondaryLight,
    onSecondary = AuraOnSecondaryLight,
    secondaryContainer = AuraSecondaryContainerLight,
    onSecondaryContainer = AuraOnSecondaryContainerLight,
    tertiary = AuraTertiaryLight,
    onTertiary = AuraOnTertiaryLight,
    tertiaryContainer = AuraTertiaryContainerLight,
    onTertiaryContainer = AuraOnTertiaryContainerLight,
    background = AuraBackgroundLight,
    onBackground = AuraOnBackgroundLight,
    surface = AuraSurfaceLight,
    onSurface = AuraOnSurfaceLight,
    surfaceVariant = AuraSurfaceVariantLight,
    onSurfaceVariant = AuraOnSurfaceVariantLight,
    outline = AuraOutlineLight,
    outlineVariant = AuraOutlineVariantLight
)

private val HighContrastLightColorScheme = lightColorScheme(
    primary = Color(0xFF6B1B30),
    onPrimary = Color.White,
    primaryContainer = Color(0xFFFFCCD6),
    onPrimaryContainer = Color.Black,
    secondary = Color(0xFF4C3038),
    onSecondary = Color.White,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    surfaceVariant = Color(0xFFE8D4D8),
    onSurfaceVariant = Color.Black,
    outline = Color(0xFF403033)
)

@Composable
fun AuraTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false, // Preserve brand identity by default
    highContrast: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        highContrast -> HighContrastLightColorScheme
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}

// Retain alias for template compatibility
@Composable
fun MyApplicationTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    AuraTheme(darkTheme = darkTheme, dynamicColor = dynamicColor, content = content)
}
