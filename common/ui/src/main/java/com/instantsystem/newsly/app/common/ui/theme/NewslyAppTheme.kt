package com.instantsystem.newsly.app.common.ui.theme

import android.os.Build
import androidx.annotation.ChecksSdkIntAtLeast
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.instantsystem.newsly.designsystem.resources.color.newsly_dark_outline
import com.instantsystem.newsly.designsystem.resources.color.newsly_light_outline
import com.instantsystem.newsly.designsystem.theme.NewslyTheme
import com.instantsystem.newsly.designsystem.theme.darkColorScheme
import com.instantsystem.newsly.designsystem.theme.lightColorScheme
import com.instantsystem.newsly.designsystem.theme.newslyDarkColors
import com.instantsystem.newsly.designsystem.theme.newslyLightColors

@Composable
fun NewslyAppTheme(darkTheme: Boolean, content: @Composable () -> Unit) {

    val newslyColors = if (darkTheme) newslyDarkColors else newslyLightColors

    val colorScheme = if (supportsDynamicTheming()) {
        val context = LocalContext.current

        if (darkTheme) {
            dynamicDarkColorScheme(context).copy(outline = newsly_dark_outline)
        } else {
            dynamicLightColorScheme(context).copy(outline = newsly_light_outline)
        }
    } else {
        if (darkTheme) darkColorScheme else lightColorScheme
    }

    NewslyTheme(
        newslyColors = newslyColors,
        colorScheme = colorScheme,
        content = content
    )
}

@ChecksSdkIntAtLeast(api = Build.VERSION_CODES.S)
fun supportsDynamicTheming() = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
