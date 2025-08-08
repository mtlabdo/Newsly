package com.instantsystem.newsly.app.di

import androidx.compose.runtime.Composable
import com.instantsystem.common.core.di.coreModules
import com.instantsystem.newsly.data.di.dataModules
import com.instantsystem.newsly.domain.di.domainModules
import org.koin.compose.KoinApplication
import org.koin.dsl.KoinAppDeclaration

@Composable
fun NewslyAppDI(
    appDeclaration: KoinAppDeclaration = {},
    content: @Composable () -> Unit
) = KoinApplication(
    application = {
        modules(presentationModules + dataModules + domainModules + coreModules + appModules)
        appDeclaration()
    },
    content = content
)