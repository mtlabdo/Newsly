package com.instantsystem.app.feature.home.ui

import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import com.instantsystem.newsly.app.common.ui.text.toUIText
import com.instantsystem.newsly.domain.model.Article
import com.instantsystem.newsly.domain.usecase.GetTopHeadlinesUseCase
import io.mockk.clearAllMocks
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.instantsystem.newsly.domain.Result


class HomeViewModelTest {

    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase
    private lateinit var viewModel: HomeViewModel
    private val testDispatcher = StandardTestDispatcher()


    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        getTopHeadlinesUseCase = mockk()

        // Default mock behavior - prevent init from running
        every { getTopHeadlinesUseCase() } returns flowOf()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `initial state should have correct default values`() {
        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
        val state = viewModel.uiState.value
        assertThat(state.isLoading).isFalse()
        assertThat(state.isRefreshing).isFalse()
        assertThat(state.news).isEmpty()
        assertThat(state.error).isNull()
    }

    @Test
    fun `loadNews should show loading state then success`() = runTest {

        every { getTopHeadlinesUseCase() } returns flowOf(
            Result.Loading,
            Result.Success(articles)
        )


        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
        testScheduler.advanceUntilIdle()


        val finalState = viewModel.uiState.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.isRefreshing).isFalse()
        assertThat(finalState.news).hasSize(2)
        assertThat(finalState.news[0].title).isEqualTo("'Knives out': Switzerland descends into blame game after US tariff shock - Financial Times")
        assertThat(finalState.news[1].title).isEqualTo("Silicon Valley's AI Spend Goes Berserk as Microsoft Starts Cashing In - Gizmodo")
        assertThat(finalState.error).isNull()
    }

    @Test
    fun `loadNews should handle network error correctly`() = runTest {

        val exception = NewsException(NewsError.NETWORK)
        every { getTopHeadlinesUseCase() } returns flowOf(
            Result.Loading,
            Result.Error(exception)
        )

        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
        testScheduler.advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.isRefreshing).isFalse()
        assertThat(finalState.news).isEmpty()
        assertThat(finalState.error).isEqualTo(NewsError.NETWORK.toUIText())
    }

    @Test
    fun `loadNews should handle unknown error correctly`() = runTest {
        val exception = RuntimeException("Unknown error")
        every { getTopHeadlinesUseCase() } returns flowOf(
            Result.Loading,
            Result.Error(exception)
        )

        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
        testScheduler.advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertThat(finalState.isLoading).isFalse()
        assertThat(finalState.news).isEmpty()
        assertThat(finalState.error).isEqualTo(NewsError.UNKNOWN.toUIText())
    }


    @Test
    fun `should transition from error to success on retry`() = runTest {
         every { getTopHeadlinesUseCase() } returns flowOf(
            Result.Error(NewsException(NewsError.NETWORK))
        )
        viewModel = HomeViewModel(getTopHeadlinesUseCase, testDispatcher)
        testScheduler.advanceUntilIdle()

         assertThat(viewModel.uiState.value.error).isNotNull()

         every { getTopHeadlinesUseCase() } returns flowOf(
            Result.Loading,
            Result.Success(articles)
        )

        viewModel.handleEvent(HomeEvent.LoadNews)
        testScheduler.advanceUntilIdle()

        val finalState = viewModel.uiState.value
        assertThat(finalState.error).isNull()
        assertThat(finalState.news).hasSize(2)
        assertThat(finalState.isLoading).isFalse()
    }


    private val articles = listOf(
        Article(
            id = "1",
            title = "'Knives out': Switzerland descends into blame game after US tariff shock - Financial Times",
            description = "President Karin Keller-Sutter under fire for failed trade talks with Trump administration",
            url = "https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b",
            imageUrl = "https://www.ft.com/__origami/service/image/v2/images/raw/https%3A%2F%2Fd1e00ek4ebabms.cloudfront.net%2Fproduction%2Fd38fc802-cfd3-4a0f-bf95-bc59ab93e11d.jpg?source=next-barrier-page",
            publishedAt = "2025-08-03T11:57:02Z",
            sourceName = "Financial Times",
            author = "Mercedes Ruehl"
        ),
        Article(
            id = "2",
            title = "Silicon Valley's AI Spend Goes Berserk as Microsoft Starts Cashing In - Gizmodo",
            description = "Tech giants reported eye-watering figures linked to artificial intelligence investment.",
            url = "https://gizmodo.com/silicon-valleys-ai-spend-goes-berserk-as-microsoft-starts-cashing-in-2000638318",
            imageUrl = "https://gizmodo.com/app/uploads/2025/07/GettyImages-2153471300-1200x675.jpg",
            publishedAt = "2025-08-03T10:00:27Z",
            sourceName = "Gizmodo.com",
            author = "Ece Yildirim"
        )
    )

}