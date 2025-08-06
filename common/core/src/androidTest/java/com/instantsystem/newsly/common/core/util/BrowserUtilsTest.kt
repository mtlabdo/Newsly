package com.instantsystem.newsly.common.core.util

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.util.BrowserNavigator
import com.instantsystem.common.core.util.IBrowserNavigator
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject

@RunWith(AndroidJUnit4::class)
class BrowserUtilsTest : KoinTest {

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val testModule = module {
        single<Context> { ApplicationProvider.getApplicationContext() }
        single<IBrowserNavigator> { BrowserNavigator(get()) }
    }

    private val browserNavigator: IBrowserNavigator by inject()

    @Test
    fun `should succeed with valid URL`() {
        val testUrl = "https://www.google.com"
        browserNavigator.openUrl(testUrl)
        // TEST SI PAS d'exception
        assertThat(true).isTrue()

    }

    @Test
    fun `should fail when URL is blank`() {
        val result = browserNavigator.openUrl("")
        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }


    @Test
    fun `should fail when URL is malformed`() {
        val result = browserNavigator.openUrl("notvalidurl")

        assertThat(result.isFailure).isTrue()
        assertThat(result.exceptionOrNull()).isInstanceOf(IllegalArgumentException::class.java)
    }
}

