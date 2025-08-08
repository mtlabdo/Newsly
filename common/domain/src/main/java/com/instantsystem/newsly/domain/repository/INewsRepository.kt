package com.instantsystem.newsly.domain.repository

import com.instantsystem.newsly.domain.model.Article
import kotlinx.coroutines.flow.Flow
import com.instantsystem.newsly.domain.Result

interface INewsRepository {
    fun getTopHeadlines(
        language: String? = null,
    ): Flow<Result<List<Article>>>
}