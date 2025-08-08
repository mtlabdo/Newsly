package com.instantsystem.newsly.app.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.app.common.ui.text.UIText
import com.instantsystem.newsly.app.common.ui.text.asString
import com.instantsystem.newsly.common.ui.R
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons

@Composable
fun ErrorContent(
    error: UIText,
    modifier: Modifier = Modifier,
    title: String = stringResource(R.string.common_ui_error_title),
    icon: ImageVector = NewslyIcons.Error,
    action: (@Composable () -> Unit)? = null
) {
    EmptyState(
        icon = icon,
        title = title,
        description = error.asString(),
        iconTint = MaterialTheme.colorScheme.error,
        modifier = modifier,
        action = action
    )
}

@Preview(showBackground = true, name = "Error Message")
@Composable
fun ErrorContentLongMessagePreview() {
    MaterialTheme {
        ErrorContent(
            error = UIText.text("Erreur de chargeemnt de l'actualités. Veuillez réessayer !"),
            title = "Erreur",
            modifier = Modifier.fillMaxSize(),
            action = {
                Button(
                    onClick = {},
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.error
                    )
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Icon(
                            imageVector = Icons.Default.Refresh,
                            contentDescription = null,
                            modifier = Modifier.size(18.dp)
                        )
                        Text("Réessayer")
                    }
                }
            }
        )
    }
}
