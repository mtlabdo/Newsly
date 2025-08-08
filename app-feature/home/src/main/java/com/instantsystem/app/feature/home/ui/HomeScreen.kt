package com.instantsystem.app.feature.home.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.instantsystem.app.feature.home.R
import com.instantsystem.common.core.util.DateTimeUtils
import com.instantsystem.newsly.app.common.ui.components.DynamicAsyncImage
import com.instantsystem.newsly.app.common.ui.components.EmptyState
import com.instantsystem.newsly.app.common.ui.components.ErrorContent
import com.instantsystem.newsly.app.common.ui.components.LoadingContent
import com.instantsystem.newsly.app.common.ui.components.NewslyHeader
import com.instantsystem.newsly.designsystem.components.ButtonVariant
import com.instantsystem.newsly.designsystem.components.NewslyButton
import com.instantsystem.newsly.designsystem.components.NewslyCard
import com.instantsystem.newsly.designsystem.resources.icon.NewslyIcons
import com.instantsystem.newsly.designsystem.tokens.NewslySpacing
import com.instantsystem.newsly.domain.model.Article
import kotlinx.collections.immutable.ImmutableList
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = koinViewModel(),
    toDetail: (Article) -> Unit = {}
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    NewslyHeader(stringResource(R.string.app_feature_home_title))
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            PullToRefreshBox(
                isRefreshing = uiState.isRefreshing,
                onRefresh = {
                    viewModel.handleEvent(HomeEvent.RefreshNews)
                },
                modifier = Modifier.fillMaxSize()
            ) {
                when {
                    uiState.isLoading && uiState.news.isEmpty() -> {
                        LoadingContent(
                            message = uiState.loadingMessage,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    uiState.error != null && uiState.news.isEmpty() -> {
                        ErrorContent(
                            error = uiState.error!!,
                            modifier = Modifier.fillMaxSize(),
                            action = {
                                Button(
                                    onClick = { viewModel.handleEvent(HomeEvent.LoadNews) },
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
                                        Text(stringResource(com.instantsystem.newsly.common.ui.R.string.common_ui_load))
                                    }
                                }
                            }
                        )
                    }

                    uiState.news.isNotEmpty() -> {
                        NewsListContent(
                            news = uiState.news,
                            onArticleClick = toDetail,
                            modifier = Modifier.fillMaxSize()
                        )
                    }

                    else -> {
                        HomeEmptyContent(
                            onRefresh = { viewModel.handleEvent(HomeEvent.LoadNews) },
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomeEmptyContent(
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier
) {
    EmptyState(
        icon = NewslyIcons.Info,
        title = stringResource(R.string.app_feature_home_no_articles_title),
        description = stringResource(R.string.app_feature_home_no_articles_description),
        modifier = modifier,
        action = {
            NewslyButton(
                text = stringResource(R.string.app_feature_home_refresh_news),
                onClick = onRefresh,
                icon = NewslyIcons.Refresh,
                variant = ButtonVariant.Primary
            )
        }
    )
}

@Composable
private fun NewsListContent(
    news: ImmutableList<Article>,
    onArticleClick: (Article) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        items(
            items = news,
            key = { article -> article.url }
        ) { article ->
            NewsItem(
                article = article,
                onClick = { onArticleClick(article) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NewsItem(
    article: Article,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    NewslyCard(
        onClick = onClick,
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(180.dp)
                    .background(MaterialTheme.colorScheme.surfaceVariant),
                contentAlignment = Alignment.Center
            ) {
                DynamicAsyncImage(
                    imageUrl = article.imageUrl,
                    contentDescription = article.title,
                    modifier = Modifier.fillMaxSize(),
                )
            }

            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall,
                    color = MaterialTheme.colorScheme.onSurface,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis
                )

                Spacer(modifier = Modifier.height(NewslySpacing.md))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = article.sourceName,
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.weight(1f, fill = false)
                    )

                    Text(
                        text = DateTimeUtils.formatToIsoDate(article.publishedAt),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
            }
        }
    }
}


