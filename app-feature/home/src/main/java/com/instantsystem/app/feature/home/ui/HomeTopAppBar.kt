package com.instantsystem.app.feature.home.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Surface
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.instantsystem.newsly.app.common.ui.components.NewslyHeader
import com.instantsystem.newsly.designsystem.theme.NewslyTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeTopAppBar() {
    Surface {
        Column {
            Box {
                TopAppBar(title = {
                    NewslyHeader()
                })
            }
        }
    }
}

@Preview
@Composable
fun HomeTopAppBarPreview() {
    NewslyTheme {
        HomeTopAppBar()
    }
}