package com.instantsystem.newsly.data.mapper

import com.instantsystem.newsly.common.remote.model.ArticleDto
import com.instantsystem.newsly.domain.model.Article

fun ArticleDto.toDomainModel(): Article {
    return Article(
        id = Article.generateId(title, publishedAt),
        title = title,
        description = description ?: "",
        url = url,
        imageUrl = urlToImage,
        publishedAt = publishedAt,
        sourceName = source.name,
        author = author
    )
}

fun List<ArticleDto>.toDomainModel(): List<Article> {
    return map { it.toDomainModel() }
}