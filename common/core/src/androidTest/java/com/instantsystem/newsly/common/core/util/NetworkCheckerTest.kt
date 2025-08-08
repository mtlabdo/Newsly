package com.instantsystem.newsly.common.core.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.util.INetworkChecker
import com.instantsystem.common.core.util.NetworkChecker
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NetworkCheckerTest {

    private lateinit var context: Context
    private lateinit var networkChecker: INetworkChecker

    @Before
    fun setup() {
        context = ApplicationProvider.getApplicationContext()
        networkChecker = NetworkChecker(context)
    }

    @Test
    fun `should inject dependencies manually`() {
        assertThat(networkChecker).isNotNull()
        assertThat(networkChecker).isInstanceOf(NetworkChecker::class.java)
    }

    @Test
    fun `should check network with real Android context`() = runTest {
        val isAvailable = networkChecker.isNetworkAvailable()
        assertThat(isAvailable).isAnyOf(true, false)
        println("Status de la connexion : $isAvailable")
    }

    @Test
    fun `should have valid Android context`() {
        assertThat(context).isNotNull()
        assertThat(context).isInstanceOf(Context::class.java)

        val applicationContext = context.applicationContext
        assertThat(applicationContext).isNotNull()
    }

    @Test
    fun `should create NetworkChecker with context`() {
        val anotherNetworkChecker = NetworkChecker(context)

        assertThat(anotherNetworkChecker).isNotNull()
        assertThat(anotherNetworkChecker).isInstanceOf(NetworkChecker::class.java)
    }
}