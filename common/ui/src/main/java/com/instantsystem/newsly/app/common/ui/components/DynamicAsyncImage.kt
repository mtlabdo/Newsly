package com.instantsystem.newsly.app.common.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.instantsystem.newsly.common.ui.R
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImagePainter
import coil.compose.AsyncImagePainter.State.Loading
import coil.compose.rememberAsyncImagePainter
import com.instantsystem.newsly.designsystem.components.LoadingIndicator
import com.instantsystem.newsly.designsystem.theme.NewslyTheme

@Composable
fun DynamicAsyncImage(
    imageUrl: String?,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    placeholder: Painter = painterResource(R.drawable.common_ui_core_ui_ic_placeholder),
) {
    var isLoading by remember { mutableStateOf(true) }
    var isError by remember { mutableStateOf(false) }

    val imageLoader = rememberAsyncImagePainter(
        model = imageUrl,
        onState = { state ->
            isLoading = state is Loading
            isError = state is AsyncImagePainter.State.Error
        },
    )

    val isLocalInspection = LocalInspectionMode.current

    Box(
        modifier = modifier
            .then(Modifier),
        contentAlignment = Alignment.Center,
    ) {
        if (isLoading && !isLocalInspection && imageUrl != null) {
            LoadingIndicator()
        }

        Image(
            painter = when {
                imageUrl == null || isError || isLocalInspection -> placeholder
                else -> imageLoader
            },
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .matchParentSize()
        )
    }
}

@Preview(showBackground = true, name = "Placeholder State")
@Composable
fun DynamicAsyncImagePlaceholderPreview() {
    NewslyTheme {
        DynamicAsyncImage(
            imageUrl = "https://instant-system.com/logo.png",
            contentDescription = "Instant system image",
            modifier = Modifier.size(150.dp)
        )
    }
}

@Preview(showBackground = true, name = "Large Size")
@Composable
fun DynamicAsyncImageLargePreview() {
    NewslyTheme {
        DynamicAsyncImage(
            imageUrl = "https://instant-system.com/logo.png",
            contentDescription = "Large instant system image",
            modifier = Modifier.size(200.dp, 150.dp)
        )
    }
}