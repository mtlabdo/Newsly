package com.instantsystem.newsly.app.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import com.instantsystem.newsly.app.common.ui.text.UIText
import com.instantsystem.newsly.app.common.ui.text.asString
import com.instantsystem.newsly.designsystem.components.LoadingIndicator
import com.instantsystem.newsly.designsystem.tokens.NewslySpacing

@Composable
fun LoadingContent(
    message: UIText,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LoadingIndicator()

            Spacer(modifier = Modifier.height(NewslySpacing.md))

            Text(
                text = message.asString(),
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
        }
    }
}

@Preview(showBackground = true, name = "Loading News")
@Composable
fun LoadingContentNewsPreview() {
    MaterialTheme {
        LoadingContent(
            message = UIText.text("Chargement des news"),
            modifier = Modifier.fillMaxSize()
        )
    }
}