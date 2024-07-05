package com.example.e_medib.ui.theme

import android.app.Activity
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

private val DarkColorPalette = darkColors(
    primary = mLightBlue,
    primaryVariant = mWhite,
    secondary = mRedSecond,
    secondaryVariant = mWhite,
)

private val LightColorPalette = lightColors(
    primary = mLightBlue,
    primaryVariant = mWhite,
    secondary = mRedSecond,
    secondaryVariant = mWhite,
    background = mWhite,
    surface = Color.White,
    onPrimary = mBlack,
    onSecondary = mWhite,
    onBackground = Color.Black,
    onSurface = Color.Black,
)

@Composable
fun EMedibTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        // DarkColorPalette
        LightColorPalette
    } else {
        LightColorPalette
    }

    val activity = LocalView.current.context as Activity
    val backgroundArgb = colors.background.toArgb()
    activity.window?.statusBarColor = backgroundArgb
    val wic = WindowCompat.getInsetsController(activity.window, activity.window.decorView)
    wic.isAppearanceLightStatusBars = !darkTheme


    MaterialTheme(
        colors = colors,
        typography = ManropeTypography,
        shapes = Shapes,
        content = content
    )
}