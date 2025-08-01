package com.instantsystem.newsly.common.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.ANDROID
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.ContentType
import io.ktor.http.contentType
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json


object KtorClient {
    const val BASE_URL: String = "https://newsapi.org/v2/"
    const val TIMEOUT: Long = 10000

    fun create(): HttpClient {
        return HttpClient(Android) {
            defaultRequest {
                url(BASE_URL)
                contentType(ContentType.Application.Json)
            }
            expectSuccess = false
            install(HttpTimeout) {
                connectTimeoutMillis = TIMEOUT
                requestTimeoutMillis = TIMEOUT
            }
            install(Logging) {
                logger = Logger.ANDROID
                level = LogLevel.BODY // TODO ADD DEBUG CHECK
                logger = object : Logger {
                    override fun log(message: String) {
                        println(message)
                    }
                }
            }
            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    explicitNulls = true
                    isLenient = true
                    encodeDefaults = true
                })
            }

            // TODO / ADD VALIDATOR EXCEPTIONS

        }
    }
}

