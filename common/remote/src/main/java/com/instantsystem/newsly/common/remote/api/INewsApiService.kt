package com.instantsystem.newsly.common.remote.api

import com.instantsystem.newsly.common.remote.model.NewsResponse
import java.util.Locale

interface INewsApiService {
    suspend fun getTopHeadlines(
        language: String?,
    ): NewsResponse
}