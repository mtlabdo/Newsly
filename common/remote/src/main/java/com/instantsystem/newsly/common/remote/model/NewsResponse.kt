package com.instantsystem.newsly.common.remote.model

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val status: String,
    val totalResults: Int,
    val articles: List<ArticleDto>
)

@Serializable
data class ArticleDto(
    val title: String,
    val description: String?,
    val url: String,
    val urlToImage: String?,
    val publishedAt: String,
    val source: SourceDto,
    val author: String? = null,
    val content: String? = null
)

@Serializable
data class SourceDto(
    val id: String?,
    val name: String
)