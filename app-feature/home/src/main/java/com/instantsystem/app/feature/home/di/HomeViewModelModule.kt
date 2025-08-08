package com.instantsystem.app.feature.home.di

import com.instantsystem.app.feature.home.ui.HomeViewModel
import com.instantsystem.common.core.di.IoDispatcher
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val homeViewModelModule = module {
    viewModel {
        HomeViewModel(
            getTopHeadlinesUseCase = get(),
            ioDispatcher = get(qualifier = IoDispatcher)
        )
    }
}