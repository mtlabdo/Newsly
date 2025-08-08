package com.instantsystem.newsly.app.feature.detail.di

import com.instantsystem.newsly.app.feature.detail.ui.DetailViewModel
import com.instantsystem.newsly.domain.model.Article
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val detailViewModelModule = module {
    viewModel { (article: Article) ->
        DetailViewModel(
            article = article
        )
    }
}