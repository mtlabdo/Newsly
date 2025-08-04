package com.instantsystem.newsly.common.remote.di

import com.instantsystem.common.core.ConfigConstants
import com.instantsystem.newsly.common.remote.api.INewsApiService
import com.instantsystem.newsly.common.remote.api.NewsApiService
import com.instantsystem.newsly.common.remote.ktor.KtorClient
import io.ktor.client.HttpClient
import org.koin.dsl.module

val remoteModule = module {

    single<HttpClient> {
        KtorClient.create(
            isDebug = get(ConfigConstants.debugModeNamedKey)
        )
    }

    single<INewsApiService> {
        NewsApiService(
            client = get(),
            apiKey = get(ConfigConstants.newsApiNamedKey)
        )
    }
}
