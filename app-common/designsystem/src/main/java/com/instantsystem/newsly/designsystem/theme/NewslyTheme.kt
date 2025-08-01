package com.instantsystem.newsly.designsystem.theme

import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf

internal val localNewslyColors = staticCompositionLocalOf { newslyLightColors() }

object NewslyTheme {
    val newslyColors: NewslyColors
        @Composable
        @ReadOnlyComposable
        get() = localNewslyColors.current
}

@Composable
fun NewslyTheme(
    newslyColors: NewslyColors = NewslyTheme.newslyColors,
    colorScheme: ColorScheme = MaterialTheme.colorScheme,
    content: @Composable () -> Unit
) {
    CompositionLocalProvider(
        localNewslyColors provides newslyColors
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            shapes = shapes,
            typography = Typography.typography,
            content = content
        )
    }
}
