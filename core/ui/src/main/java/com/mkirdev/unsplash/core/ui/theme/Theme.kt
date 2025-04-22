package com.mkirdev.unsplash.core.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorScheme = darkColorScheme(
    primary = black,
    onPrimary = white,
    primaryContainer = white,
    onPrimaryContainer = black,
    surface = dark_grey,
    surfaceVariant = green,
    onSurfaceVariant = black,
    inverseSurface = very_dark_grey,
    inverseOnSurface = black_transparent,
    secondaryContainer = dark_grey,
    onSecondaryContainer = white,
    background = dark_grey,
    onBackground = white,
    error = red_error
)

private val LightColorScheme = lightColorScheme(
    primary = black,
    onPrimary = white,
    primaryContainer = black,
    onPrimaryContainer = white,
    surface = white,
    surfaceVariant = green,
    onSurfaceVariant = black,
    inverseSurface = light_grey,
    inverseOnSurface = black_transparent,
    secondaryContainer = white,
    onSecondaryContainer = black,
    background = white,
    onBackground = black,
    error = red_error

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun UnsplashTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}