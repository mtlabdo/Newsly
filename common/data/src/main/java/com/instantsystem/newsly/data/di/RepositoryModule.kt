package com.instantsystem.newsly.data.di

import com.instantsystem.common.core.di.IoDispatcher
import com.instantsystem.newsly.data.repository.NewsRepository
import com.instantsystem.newsly.domain.repository.INewsRepository
import org.koin.dsl.module


internal val repositoryModule = module {

    // News Repository
    single<INewsRepository> {
        NewsRepository(
            remoteDataSource = get(),
            networkChecker = get(),
            ioDispatcher = get(qualifier = IoDispatcher)
        )
    }
}