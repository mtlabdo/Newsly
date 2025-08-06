package com.instantsystem.newsly.common.core.di

import com.instantsystem.common.core.di.DefaultDispatcher
import com.instantsystem.common.core.di.IoDispatcher
import com.instantsystem.common.core.di.MainDispatcher
import com.instantsystem.common.core.di.dispatchersModule
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.junit.Test
import org.koin.dsl.koinApplication
import org.koin.test.KoinTest
import kotlin.test.assertEquals
import kotlin.test.assertNotNull


class DispatcherCoroutinesModulesTest : KoinTest {

    @Test
    fun `should provide correct dispatchers`() {
        val app = koinApplication {
            modules(dispatchersModule)
        }

        val ioDispatcher = app.koin.get<CoroutineDispatcher>(IoDispatcher)
        val mainDispatcher = app.koin.get<CoroutineDispatcher>(MainDispatcher)
        val defaultDispatcher = app.koin.get<CoroutineDispatcher>(DefaultDispatcher)

        assertNotNull(ioDispatcher)
        assertNotNull(mainDispatcher)
        assertNotNull(defaultDispatcher)

        assertEquals(Dispatchers.IO, ioDispatcher)
        assertEquals(Dispatchers.Main, mainDispatcher)
        assertEquals(Dispatchers.Default, defaultDispatcher)
    }



    @Test
    fun `should provide singletons for dispatchers`() {
        val app = koinApplication {
            modules(dispatchersModule)
        }

        val ioDispatcher1 = app.koin.get<CoroutineDispatcher>(IoDispatcher)
        val ioDispatcher2 = app.koin.get<CoroutineDispatcher>(IoDispatcher)

        assertEquals(ioDispatcher1, ioDispatcher2)
    }
}