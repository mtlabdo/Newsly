package com.instantsystem.newsly.data.repository

import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import com.instantsystem.common.core.util.INetworkChecker
import com.instantsystem.newsly.common.remote.api.INewsApiService
import com.instantsystem.newsly.data.mapper.toDomainModel
import com.instantsystem.newsly.domain.model.Article
import com.instantsystem.newsly.domain.repository.INewsRepository
import com.instantsystem.newsly.domain.Result
import com.instantsystem.newsly.domain.asResult
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class NewsRepository(
    private val remoteDataSource: INewsApiService,
    private val networkChecker: INetworkChecker,
    val ioDispatcher: CoroutineDispatcher
) : INewsRepository {

    override fun getTopHeadlines(
        language: String?,
    ): Flow<Result<List<Article>>> {
        return flow {
            if (!networkChecker.isNetworkAvailable()) {
                throw NewsException(NewsError.NETWORK)
            }

            val lang = language
            val response = remoteDataSource.getTopHeadlines(lang)
            val articles = response.articles.toDomainModel()

            // TODO: Save to cache

            emit(articles)
        }
            .asResult()
            .flowOn(ioDispatcher)
    }
}