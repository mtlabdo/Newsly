package com.instantsystem.newsly.designsystem.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.theme.NewslyTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewslyCard(
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    elevation: Dp = 2.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val cardModifier = if (onClick != null) {
        modifier.clickable { onClick() }
    } else {
        modifier
    }

    Card(
        onClick = onClick ?: {},
        modifier = cardModifier,
        elevation = CardDefaults.cardElevation(
            defaultElevation = elevation,
            pressedElevation = elevation + 6.dp
        ),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surface
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            content = content
        )
    }
}


@Preview(showBackground = true)
@Composable
fun NewslyCardBasicPreview() {
    NewslyTheme {
        NewslyCard {
            Text(
                text = "Title",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Content",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun NewslyCardClickablePreview() {
    NewslyTheme {
        NewslyCard {
            Text(
                text = "Clickable Card",
                style = MaterialTheme.typography.headlineSmall
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "Content card",
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

