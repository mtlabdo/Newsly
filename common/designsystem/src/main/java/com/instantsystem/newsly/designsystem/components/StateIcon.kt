package com.instantsystem.newsly.designsystem.components

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons
import com.instantsystem.newsly.designsystem.theme.NewslyTheme

@Composable
fun StateIcon(
    icon: ImageVector,
    contentDescription: String? = null,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    tint: Color = MaterialTheme.colorScheme.outline
) {
    Icon(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier.size(size),
        tint = tint
    )
}

@Preview(showBackground = true)
@Composable
fun StateIconDefaultPreview() {
    NewslyTheme {
        StateIcon(
            icon = NewslyIcons.ArrowBack,
            contentDescription = "Back"
        )
    }
}

@Preview(showBackground = true)
@Composable
fun StateIconColorPreview() {
    NewslyTheme {
        StateIcon(
            icon = NewslyIcons.ArrowBack,
            contentDescription = "Success",
            tint = MaterialTheme.colorScheme.error
        )
    }
}