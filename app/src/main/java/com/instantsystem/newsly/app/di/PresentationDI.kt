package com.instantsystem.newsly.app.di
import com.instantsystem.app.feature.home.di.homeViewModelModule
import com.instantsystem.newsly.app.feature.detail.di.detailViewModelModule

val presentationModules = homeViewModelModule + detailViewModelModule