package com.instantsystem.app.feature.home.ui

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.instantsystem.newsly.designsystem.theme.NewslyTheme

@Composable
fun HomeScreen() {

    Scaffold(
        topBar = {
            HomeTopAppBar()
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(HomeSpace),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = "Hello World",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(16.dp))

        }
    }
}


@Preview
@Composable
fun HomeScreenPreview() {
    NewslyTheme {
        HomeScreen()
    }
}

private val HomeSpace = 16.dp
