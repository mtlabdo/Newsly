package com.instantsystem.newsly.common.remote.api

import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import com.instantsystem.common.core.exception.toNewsException
import com.instantsystem.newsly.common.remote.model.NewsResponse
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.http.HttpStatusCode

class NewsApiService(
    private val client: HttpClient,
    private val apiKey: String
) : INewsApiService {

    override suspend fun getTopHeadlines(
        language: String?,
    ): NewsResponse {
        return try {
            val response = client.get("top-headlines") {
                parameter("language", language)
                parameter("apiKey", apiKey)
            }.body<NewsResponse>()
           response
        } catch (e: ClientRequestException) {
            when (e.response.status) {
                HttpStatusCode.Unauthorized -> throw NewsException(NewsError.AUTH)
                HttpStatusCode.TooManyRequests -> throw NewsException(NewsError.LIMIT)
                else -> throw NewsException(NewsError.UNKNOWN)
            }
        } catch (e: Exception) {
            throw e.toNewsException()
        }
    }
}

