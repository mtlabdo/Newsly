package com.instantsystem.newsly.navhost

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.instantsystem.app.feature.home.ui.HomeViewModel
import com.instantsystem.newsly.app.common.ui.theme.NewslyAppTheme
import com.instantsystem.newsly.app.di.NewslyAppDI
import com.instantsystem.newsly.app.navhost.NewslyNavHost
import com.instantsystem.newsly.domain.usecase.GetTopHeadlinesUseCase
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NewslyNavHostIntegrationTest {

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()
    
    @get:Rule
    val composeTestRule = createComposeRule()
    
    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTopHeadlinesUseCase = mockk()
        every { getTopHeadlinesUseCase() } returns flowOf()
        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
    }
    

    @Test
    fun `should display NavHost with NewslyAppDI`() {
        composeTestRule.setContent {
            NewslyAppDI {
                NewslyAppTheme(darkTheme = false) {
                    NewslyNavHost()
                }
            }
        }

        composeTestRule.waitForIdle()
        composeTestRule.onRoot().assertIsDisplayed()
    }
    
}