package com.instantsystem.newsly.domain.usecase

import com.instantsystem.newsly.domain.Result
import com.instantsystem.newsly.domain.model.Article
import com.instantsystem.newsly.domain.repository.INewsRepository
import kotlinx.coroutines.flow.Flow

class GetTopHeadlinesUseCase(
    private val repository: INewsRepository
) {

    operator fun invoke(
        language: String = "en",
    ): Flow<Result<List<Article>>> {
        return repository.getTopHeadlines(language)
    }
}