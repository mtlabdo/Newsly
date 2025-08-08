package com.instantsystem.newsly.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun LoadingIndicator(
    modifier: Modifier = Modifier,
    size: Dp = 48.dp,
    strokeWidth: Dp = 4.dp,
    color: Color = MaterialTheme.colorScheme.primary
) {
    CircularProgressIndicator(
        modifier = modifier.size(size),
        strokeWidth = strokeWidth,
        color = color
    )
}

@Preview(showBackground = true)
@Composable
fun LoadingIndicatorPreview() {
    MaterialTheme {
        LoadingIndicator()
    }
}

@Preview(showBackground = true, name = "Custom Color")
@Composable
fun LoadingIndicatorCustomColorPreview() {
    MaterialTheme {
        LoadingIndicator(
            color = Color.Red
        )
    }
}