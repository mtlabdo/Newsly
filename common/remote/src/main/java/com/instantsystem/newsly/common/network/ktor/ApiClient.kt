package com.instantsystem.newsly.common.network.ktor

import io.ktor.client.HttpClient
import io.ktor.client.engine.android.*
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.DEFAULT
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.http.takeFrom
import kotlinx.serialization.json.Json
import io.ktor.serialization.kotlinx.json.json

const val BASE_URL : String = "https://newsapi.org/v2/"
const val timeout : Long = 10000

val apiClient = HttpClient(Android) {
    defaultRequest {
        url {
            takeFrom(BASE_URL)
        }
    }
    expectSuccess = false
    install(HttpTimeout) {
        connectTimeoutMillis = timeout
        requestTimeoutMillis = timeout
    }
    install(Logging) {
        logger = Logger.DEFAULT
        level = LogLevel.ALL
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