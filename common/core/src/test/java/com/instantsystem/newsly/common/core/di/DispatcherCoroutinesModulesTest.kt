package com.instantsystem.newsly.common.core.di

import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.di.DefaultDispatcher
import com.instantsystem.common.core.di.IoDispatcher
import com.instantsystem.common.core.di.MainDispatcher
import com.instantsystem.common.core.di.dispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest

class DispatcherCoroutinesModulesTest : KoinTest {

    @Test
    fun `should provide correct dispatchers`() {
        val app = koinApplication {
            modules(dispatchersModule)
        }

        val ioDispatcher = app.koin.get<CoroutineDispatcher>(IoDispatcher)
        val mainDispatcher = app.koin.get<CoroutineDispatcher>(MainDispatcher)
        val defaultDispatcher = app.koin.get<CoroutineDispatcher>(DefaultDispatcher)

        assertThat(ioDispatcher).isNotNull()
        assertThat(mainDispatcher).isNotNull()
        assertThat(defaultDispatcher).isNotNull()

        assertThat(ioDispatcher).isEqualTo(Dispatchers.IO)
        assertThat(mainDispatcher).isEqualTo(Dispatchers.Main)
        assertThat(defaultDispatcher).isEqualTo(Dispatchers.Default)
    }

    @Test
    fun `should provide singletons for dispatchers`() {
        val app = koinApplication {
            modules(dispatchersModule)
        }

        val ioDispatcher1 = app.koin.get<CoroutineDispatcher>(IoDispatcher)
        val ioDispatcher2 = app.koin.get<CoroutineDispatcher>(IoDispatcher)

        assertThat(ioDispatcher1).isSameInstanceAs(ioDispatcher2)
    }
}