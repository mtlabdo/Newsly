package com.instantsystem.newsly.designsystem.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons
import com.instantsystem.newsly.designsystem.theme.NewslyTheme
import com.instantsystem.newsly.designsystem.tokens.NewslySpacing

@Composable
fun NewslyButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    icon: ImageVector? = null,
    enabled: Boolean = true,
    variant: ButtonVariant = ButtonVariant.Primary
) {
    val colors = when (variant) {
        ButtonVariant.Primary -> ButtonDefaults.buttonColors()
        ButtonVariant.Secondary -> ButtonDefaults.outlinedButtonColors()
    }

    Button(
        onClick = onClick,
        modifier = modifier,
        enabled = enabled,
        colors = colors
    ) {
        icon?.let {
            Icon(
                imageVector = it,
                contentDescription = null,
                modifier = Modifier.size(18.dp)
            )
            Spacer(modifier = Modifier.width(NewslySpacing.sm))

        }
        Text(text)
    }
}

enum class ButtonVariant { Primary, Secondary }



@Preview(showBackground = true, name = "Buttons variants")
@Composable
fun NewslyButtonAllVariantsPreview() {
    NewslyTheme {
        Column(
            modifier = Modifier.padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            NewslyButton(
                text = "Primary",
                onClick = { },
                variant = ButtonVariant.Primary
            )

            NewslyButton(
                text = "Secondary",
                onClick = { },
                variant = ButtonVariant.Secondary
            )

            NewslyButton(
                text = "With Icon",
                onClick = { },
                icon = NewslyIcons.ArrowBack,
                variant = ButtonVariant.Primary
            )

            NewslyButton(
                text = "Disabled",
                onClick = { },
                enabled = false,
                variant = ButtonVariant.Primary
            )
        }
    }
}