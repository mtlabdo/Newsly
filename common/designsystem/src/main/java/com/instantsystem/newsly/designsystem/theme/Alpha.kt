package com.instantsystem.newsly.designsystem.theme

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

object Alpha {
    const val High: Float = 1f
    const val Medium: Float = 0.6f
    const val Disabled: Float = 0.38f

    @Composable
    fun Color.applyHighEmphasisAlpha(): Color = withAlpha(High)

    @Composable
    fun Color.applyMediumEmphasisAlpha(): Color = withAlpha(Medium)

    @Composable
    fun Color.applyDisabledEmphasisAlpha(): Color = withAlpha(Disabled)
}

@Composable
fun Color.withAlpha(alpha: Float): Color = copy(alpha = alpha)
