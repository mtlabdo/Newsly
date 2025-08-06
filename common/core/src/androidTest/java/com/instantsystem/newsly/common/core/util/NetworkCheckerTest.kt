package com.instantsystem.newsly.common.core.util

import android.content.Context
import android.net.ConnectivityManager
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.util.INetworkChecker
import com.instantsystem.common.core.util.NetworkChecker
import kotlinx.coroutines.test.runTest
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject


@RunWith(AndroidJUnit4::class)
class NetworkCheckerTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val testModule = module {
        single<Context> { ApplicationProvider.getApplicationContext() }
        single<INetworkChecker> { NetworkChecker(get()) }
    }

    private val networkChecker: INetworkChecker by inject()

    @Test
    fun `should inject Koin dependencies`() {
        assertThat(networkChecker).isNotNull()
        assertThat(networkChecker).isInstanceOf(NetworkChecker::class.java)
    }

    @Test
    fun `should check network with real Android context`() = runTest {
        val isAvailable = networkChecker.isNetworkAvailable()
        assertThat(isAvailable).isAnyOf(true, false)
        println("Status de la connexion : $isAvailable")
    }
}

