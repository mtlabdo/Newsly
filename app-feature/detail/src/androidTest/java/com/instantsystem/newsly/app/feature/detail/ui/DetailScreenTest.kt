package com.instantsystem.newsly.app.feature.detail.ui

import android.content.Context
import androidx.compose.ui.test.assertHasClickAction
import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.google.common.truth.Truth.assertThat
import com.instantsystem.app.feature.detail.R
import com.instantsystem.newsly.designsystem.theme.NewslyTheme
import com.instantsystem.newsly.domain.model.Article
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.MutableStateFlow
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class DetailScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val context: Context
        get() = ApplicationProvider.getApplicationContext()


    @Test
    fun `detailScreen should display article title and content`() {
        val mockViewModel = mockk<DetailViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            DetailUiState(article = article)
        )

        composeTestRule.setContent {
            NewslyTheme {
                DetailScreen(
                    viewModel = mockViewModel,
                    onNavigateBack = { },
                    onOpenInBrowser = { }
                )
            }
        }

        composeTestRule
            .onNodeWithText(article.title)
            .assertIsDisplayed()

        composeTestRule
            .onNodeWithText(article.sourceName)
            .assertIsDisplayed()
    }

    @Test
    fun `detailScreen should trigger browser callback when read more is clicked`() {
        val mockViewModel = mockk<DetailViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            DetailUiState(article = article)
        )

        var openedUrl: String? = null

        composeTestRule.setContent {
            NewslyTheme {
                DetailScreen(
                    viewModel = mockViewModel,
                    onOpenInBrowser = { url -> openedUrl = url }
                )
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_detail_read_more))
            .performClick()

        assertThat(openedUrl).isEqualTo(article.url)
    }

    @Test
    fun `detailScreen should display read more button with correct text`() {
        val mockViewModel = mockk<DetailViewModel>(relaxed = true)
        every { mockViewModel.uiState } returns MutableStateFlow(
            DetailUiState(article = article)
        )

        composeTestRule.setContent {
            NewslyTheme {
                DetailScreen(
                    viewModel = mockViewModel,

                )
            }
        }

        composeTestRule
            .onNodeWithText(context.getString(R.string.app_feature_detail_read_more))
            .assertIsDisplayed()
            .assertHasClickAction()
    }

}

private val article = Article(
    id = "detail-test-1",
    title = "Breaking: Switzerland descends into blame game after US tariff shock",
    description = "President Karin Keller-Sutter under fire for failed trade talks with Trump administration. The political fallout continues as various parties point fingers.",
    url = "https://www.ft.com/content/test-article",
    imageUrl = "https://example.com/test-image.jpg",
    publishedAt = "2025-08-03T11:57:02Z",
    sourceName = "Financial Times",
    author = "Mercedes Ruehl"
)