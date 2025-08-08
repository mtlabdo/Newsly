package com.instantsystem.newsly.app.di

import com.instantsystem.common.core.ConfigConstants
import com.instantsystem.newsly.BuildConfig
import org.koin.dsl.module

val appModules = module {
    single(ConfigConstants.newsApiNamedKey) { BuildConfig.API_KEY }
    single(ConfigConstants.debugModeNamedKey) { BuildConfig.DEBUG }
}

