package com.instantsystem.newsly.designsystem.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.unit.sp
import com.instantsystem.newsly.designsystem.resources.type.quickSandFontFamily

internal object Typography {
    val typography = Typography(
        headlineLarge = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 32.sp
        ),
        headlineMedium = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 28.sp
        ),
        headlineSmall = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 22.sp,
            letterSpacing = 0.15.sp
        ),
        titleLarge = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 20.sp
        ),
        titleMedium = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        ),
        titleSmall = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp
        ),
        bodyLarge = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            baselineShift = BaselineShift(+0.1f)
        ),
        bodyMedium = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 14.sp,
            baselineShift = BaselineShift(+0.3f)
        ),
        bodySmall = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        labelLarge = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        labelMedium = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 12.sp
        ),
        labelSmall = TextStyle(
            fontFamily = quickSandFontFamily,
            fontWeight = FontWeight.Medium,
            fontSize = 10.sp,
            letterSpacing = 1.5.sp
        )
    )
}
