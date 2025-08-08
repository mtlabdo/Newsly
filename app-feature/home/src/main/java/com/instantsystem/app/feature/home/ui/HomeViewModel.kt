package com.instantsystem.app.feature.home.ui

import androidx.lifecycle.viewModelScope
import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import com.instantsystem.newsly.app.common.ui.base.BaseEvent
import com.instantsystem.newsly.app.common.ui.base.BaseViewModel
import com.instantsystem.newsly.app.common.ui.text.toUIText
import com.instantsystem.newsly.domain.model.Article
import com.instantsystem.newsly.domain.usecase.GetTopHeadlinesUseCase
import kotlinx.coroutines.launch
import com.instantsystem.newsly.domain.Result
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job

class HomeViewModel(
    private val getTopHeadlinesUseCase: GetTopHeadlinesUseCase,
    private val ioDispatcher: CoroutineDispatcher
) : BaseViewModel<HomeUiState>(
    initialUIState = HomeUiState()
) {

    private var loadingJob: Job? = null

    init {
        loadNews()
    }

    private fun loadNews() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch(ioDispatcher) {
            getTopHeadlinesUseCase()
                .collect { result ->
                    handleResult(result, isRefresh = false)
                }

        }
    }

    private fun refreshNews() {
        loadingJob?.cancel()
        loadingJob = viewModelScope.launch {
            updateUIState { it.copy(isRefreshing = true, error = null) }

            getTopHeadlinesUseCase()
                .collect { result ->
                    handleResult(result, isRefresh = true)
                }
        }
    }


    private fun handleResult(result: Result<List<Article>>, isRefresh: Boolean) {
        when (result) {
            is Result.Loading -> {
                if (!isRefresh) {
                    updateUIState {
                        it.copy(isLoading = true, error = null)
                    }
                }
            }


            is Result.Success -> {
                updateUIState {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        news = result.data.toImmutableList(),
                        error = null
                    )
                }
            }

            is Result.Error -> {
                val exception = result.exception
                val newsError = when (exception) {
                    is NewsException -> exception.errorType
                    else -> NewsError.UNKNOWN
                }

                updateUIState {
                    it.copy(
                        isLoading = false,
                        isRefreshing = false,
                        error = newsError.toUIText()
                    )
                }
            }
        }
    }

    override fun handleEvent(event: BaseEvent) {
        when (event) {
            is HomeEvent.LoadNews -> loadNews()
            is HomeEvent.RefreshNews -> refreshNews()
        }
    }
}

