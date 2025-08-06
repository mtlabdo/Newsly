package com.instantsystem.common.core.exception


data class NewsException(
    val errorType: NewsError,
    val serverCode: Int? = null,
    override val message: String = errorType.name
) : Exception(message)


