package com.instantsystem.newsly.domain.model

import kotlinx.serialization.Serializable

@Serializable
data class Article(
    val id: String,
    val title: String,
    val description: String,
    val url: String,
    val imageUrl: String?,
    val publishedAt: String,
    val sourceName: String,
    val author: String? = null
) {
    companion object {
        fun generateId(title: String, publishedAt: String): String {
            return "${title.hashCode()}_${publishedAt.hashCode()}".replace("-", "")
        }
    }
}