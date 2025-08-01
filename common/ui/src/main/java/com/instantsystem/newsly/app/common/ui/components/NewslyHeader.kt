package com.instantsystem.newsly.app.common.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons

@Composable
fun NewslyHeader(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(NewslyHeaderTextPaddingStart)
    ) {

        Icon(
            NewslyIcons.List,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(NewslyHeaderTitleIconSize)
        )
        Text(
            text = "Newsly", // TODO MAKE STRING RESOURCES WITH TRANSLATE
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(
                bottom = NewslyHeaderTextPaddingBottom
            ),
            fontWeight = FontWeight.Bold
        )
    }
}

private val NewslyHeaderTextPaddingStart: Dp = 8.dp
private val NewslyHeaderTitleIconSize: Dp = 32.dp
private val NewslyHeaderTextPaddingBottom: Dp = 4.dp

