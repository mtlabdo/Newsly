package com.instantsystem.newsly.designsystem.resources.type

import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.instantsystem.newsly.common.designsystem.R

val quickSandLightFont = Font(R.font.quicksand_regular, FontWeight.Light)
internal val quickSandRegularFont = Font(R.font.quicksand_regular, FontWeight.Normal)
internal val quickSandMediumFont = Font(R.font.quicksand_medium, FontWeight.Medium)
internal val quickSandSemiBoldFont = Font(R.font.quicksand_semi_bold, FontWeight.SemiBold)
internal val quickSandBoldFont = Font(R.font.quicksand_semi_bold, FontWeight.Bold)

 val quickSandFontFamily = FontFamily(
    quickSandLightFont,
    quickSandRegularFont,
    quickSandMediumFont,
    quickSandSemiBoldFont,
    quickSandBoldFont
)
