package com.instantsystem.newsly.app.feature.detail.ui

import com.instantsystem.newsly.app.common.ui.base.BaseEvent
import com.instantsystem.newsly.app.common.ui.base.BaseViewModel
import com.instantsystem.newsly.domain.model.Article

class DetailViewModel(
    private val article: Article,
) : BaseViewModel<DetailUiState>(
    initialUIState = DetailUiState(article = article)
) {
    override fun handleEvent(event: BaseEvent) = Unit
}
