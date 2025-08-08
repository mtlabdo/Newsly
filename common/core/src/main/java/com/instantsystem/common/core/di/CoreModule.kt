package com.instantsystem.common.core.di

import com.instantsystem.common.core.util.BrowserNavigator
import com.instantsystem.common.core.util.IBrowserNavigator
import com.instantsystem.common.core.util.INetworkChecker
import com.instantsystem.common.core.util.NetworkChecker
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.koin.core.module.dsl.singleOf
import org.koin.core.qualifier.named
import org.koin.dsl.bind
import org.koin.dsl.module

val IoDispatcher = named("IODispatcher")
val MainDispatcher = named("MainDispatcher")
val DefaultDispatcher = named("DefaultDispatcher")

val dispatchersModule = module {
    single<CoroutineDispatcher>(IoDispatcher) { Dispatchers.IO }
    single<CoroutineDispatcher>(MainDispatcher) { Dispatchers.Main }
    single<CoroutineDispatcher>(DefaultDispatcher) { Dispatchers.Default }

}

val browserNavigatorModule = module {
    single<IBrowserNavigator> {
        BrowserNavigator(context = get())
    }
}

val internetCheckerModule = module {
    singleOf(::NetworkChecker) bind INetworkChecker::class
}