package com.saswat10.posthive.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = DraculaPurple,
    secondary = DraculaGreen,
    tertiary = DraculaComment,
    background = DraculaBackground,
    surface = DraculaCurrentLine,
    onPrimary = DraculaForeground,
    onSecondary = DraculaBackground,
    onTertiary = DraculaBackground,
    onBackground = DraculaForeground,
    onSurface = DraculaForeground,
    error = DraculaRed,
)

private val LightColorScheme = lightColorScheme(
    primary = AlucardPurple,
    secondary = AlucardPink,
    tertiary = AlucardComment,
    background = AlucardBackground,
    surface = AlucardCurrentLine,
    onPrimary = AlucardBackground,
    onSecondary = AlucardBackground,
    onTertiary = AlucardBackground,
    onBackground = AlucardForeground,
    onSurface = AlucardForeground,
    error = AlucardRed,
)

@Composable
fun PostHiveTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = false,
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

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}