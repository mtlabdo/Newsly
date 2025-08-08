package com.instantsystem.newsly.app.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButtonDefaults.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.components.StateIcon
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons
import com.instantsystem.newsly.designsystem.tokens.NewslySpacing

@Composable
fun EmptyState(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier = Modifier,
    iconTint: Color = MaterialTheme.colorScheme.outline,
    iconSize: Dp = 64.dp,
    action: (@Composable () -> Unit)? = null
) {
    Box(
        modifier = modifier,
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = Modifier.padding(NewslySpacing.lg)
        ) {
            StateIcon(
                icon = icon,
                tint = iconTint,
                size = iconSize
            )

            Spacer(modifier = Modifier.height(NewslySpacing.md))

            if (title.isNotEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
                Spacer(modifier = Modifier.height(NewslySpacing.sm))

            }

            if (description.isNotEmpty()) {
                Text(
                    text = description,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.outline,
                    textAlign = TextAlign.Center
                )
            }

            action?.let {
                Spacer(modifier = Modifier.height(24.dp))
                it()
            }
        }
    }
}

@Preview(showBackground = true, name = "Empty content with action Button")
@Composable
fun EmptyStateWithActionPreview() {
    MaterialTheme {
        EmptyState(
            icon = NewslyIcons.Refresh,
            title = "Aucune actualité trouvée",
            description = "Vérifiez votre connexion internet ou essayez de rafraîchir la page.",
            modifier = Modifier.fillMaxSize(),
            action = {
                Button(onClick = { }) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        androidx.compose.material3.Icon(
                            imageVector = NewslyIcons.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text("Actualiser")
                    }
                }
            }
        )
    }
}