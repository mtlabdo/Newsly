package com.instantsystem.newsly.common.network.exception

sealed class NewsApiException(
    message: String,
    cause: Throwable? = null
) : Exception(message, cause) {

    object Unauthorized : NewsApiException(
        userMessage = UIText.StringResource(R.string.error_api_key),
        message = "Invalid API key"
    )
}