package com.instantsystem.newsly.domain.di

import com.instantsystem.newsly.domain.usecase.GetTopHeadlinesUseCase
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val useCaseModule = module {

    // News Use cases
    singleOf(::GetTopHeadlinesUseCase)
}