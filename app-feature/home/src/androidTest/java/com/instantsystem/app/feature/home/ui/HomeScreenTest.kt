package com.instantsystem.app.feature.home.ui

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.performClick
import androidx.compose.ui.test.performTouchInput
import androidx.compose.ui.test.swipeDown
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.instantsystem.app.feature.home.R
import com.instantsystem.newsly.designsystem.theme.NewslyTheme
import com.instantsystem.newsly.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenComposableTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context
        get() = ApplicationProvider.getApplicationContext()

    @Test
    fun `homeEmptyContent should display title and description when no articles available`() {
        composeTestRule.setContent {
            NewslyTheme {
                HomeEmptyContent(onRefresh = { })
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_no_articles_title))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_no_articles_description))
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_refresh_news))
            .assertIsDisplayed()
            .assertHasClickAction()
    }


    @Test
    fun `homeEmptyContent should display refresh button with correct text`() {
        composeTestRule.setContent {
            NewslyTheme {
                HomeEmptyContent(onRefresh = { })
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_refresh_news))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

    @Test
    fun `homeEmptyContent should trigger callback when refresh button is clicked`() {
        var refreshClicked = false

        composeTestRule.setContent {
            NewslyTheme {
                HomeEmptyContent(onRefresh = { refreshClicked = true })
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_refresh_news))
            .performClick()

        assertThat(refreshClicked).isTrue()
    }

    @Test
    fun `homeScreen should show empty state when no articles are loaded`() {
        val mockViewModel = mockk<HomeViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            HomeUiState(
                isLoading = false,
                news = persistentListOf()
            )
        )

        composeTestRule.setContent {
            NewslyTheme {
                HomeScreen(viewModel = mockViewModel)
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_home_no_articles_title))
            .assertIsDisplayed()
    }

    @Test
    fun `homeScreen should display articles list when data is loaded successfully`() {

        val mockViewModel = mockk<HomeViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            HomeUiState(
                isLoading = false,
                news = articles,
            )
        )

        composeTestRule.setContent {
            NewslyTheme {
                HomeScreen(viewModel = mockViewModel, toDetail = { })
            }
        }

        composeTestRule
            .onNodeWithText("Switzerland descends into blame game after US tariff shock")
            .assertIsDisplayed()
    }

    @Test
    fun `homeScreen should trigger RefreshNews event when pull to refresh is performed`() {
        val mockViewModel = mockk<HomeViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            HomeUiState(
                isLoading = false,
                isRefreshing = false,
                news = persistentListOf()
            )
        )

        composeTestRule.setContent {
            NewslyTheme {
                HomeScreen(viewModel = mockViewModel, toDetail = { })
            }
        }

        // Simulate pull to refresh
        composeTestRule
            .onRoot()
            .performTouchInput {
                swipeDown()
            }

        verify { mockViewModel.handleEvent(HomeEvent.RefreshNews) }
    }

    @Test
    fun `homeScreen should navigate to detail when article is clicked`() {
        val mockViewModel = mockk<HomeViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            HomeUiState(
                isLoading = false,
                news = articles,
            )
        )

        var navigatedArticle: Article? = null

        composeTestRule.setContent {
            NewslyTheme {
                HomeScreen(
                    viewModel = mockViewModel,
                    toDetail = { article -> navigatedArticle = article }
                )
            }
        }

        composeTestRule
            .onNodeWithText("Switzerland descends into blame game after US tariff shock")
            .performClick()

        assertThat(navigatedArticle).isEqualTo(articles[0])
    }
}


private val articles = persistentListOf(
    Article(
        id = "1",
        title = "Switzerland descends into blame game after US tariff shock",
        description = "President Karin Keller-Sutter under fire for failed trade talks",
        url = "https://www.ft.com/content/test",
        imageUrl = "https://example.com/image1.jpg",
        publishedAt = "2025-08-03T11:57:02Z",
        sourceName = "Financial Times",
        author = "Mercedes Ruehl"
    )
)
