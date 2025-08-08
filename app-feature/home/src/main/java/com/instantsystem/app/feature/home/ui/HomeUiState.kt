package com.instantsystem.app.feature.home.ui

import com.instantsystem.newsly.app.common.ui.base.BaseUIState
import com.instantsystem.newsly.app.common.ui.text.UIText
import com.instantsystem.newsly.common.ui.R
import com.instantsystem.newsly.domain.model.Article
import kotlinx.collections.immutable.ImmutableList
import kotlinx.collections.immutable.persistentListOf

data class HomeUiState(
    val isLoading: Boolean = false,
    val isRefreshing: Boolean = false,

    val news: ImmutableList<Article> = persistentListOf(),
    val error: UIText? = null,
    val loadingMessage: UIText = UIText.resource(R.string.common_ui_loading_news),
) : BaseUIState