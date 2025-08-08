package com.instantsystem.newsly.app.feature.detail.ui

import com.instantsystem.newsly.app.common.ui.base.BaseUIState
import com.instantsystem.newsly.domain.model.Article

data class DetailUiState(
    val article: Article
) : BaseUIState
