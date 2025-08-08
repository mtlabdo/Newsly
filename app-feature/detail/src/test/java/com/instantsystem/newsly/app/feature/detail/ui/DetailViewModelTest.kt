package com.instantsystem.newsly.app.feature.detail.ui

import com.google.common.truth.Truth.assertThat
import com.instantsystem.newsly.domain.model.Article
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

class DetailViewModelTest {

    private lateinit var viewModel: DetailViewModel
    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `should initialize with correct article`() {
        viewModel = DetailViewModel(article)

        val state = viewModel.uiState.value
        assertThat(state.article).isEqualTo(article)
        assertThat(state.article.id).isEqualTo("detail-test-1")
        assertThat(state.article.title).isEqualTo("'Knives out': Switzerland descends into blame game after US tariff shock - Financial Times")
        assertThat(state.article.sourceName).isEqualTo("Financial Times")
        assertThat(state.article.author).isEqualTo("Mercedes Ruehl")
    }
}

private val article = Article(
    id = "detail-test-1",
    title = "'Knives out': Switzerland descends into blame game after US tariff shock - Financial Times",
    description = "President Karin Keller-Sutter under fire for failed trade talks with Trump administration",
    url = "https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b",
    imageUrl = "https://www.ft.com/__origami/service/image/v2/images/raw/https%3A%2F%2Fd1e00ek4ebabms.cloudfront.net%2Fproduction%2Fd38fc802-cfd3-4a0f-bf95-bc59ab93e11d.jpg?source=next-barrier-page",
    publishedAt = "2025-08-03T11:57:02Z",
    sourceName = "Financial Times",
    author = "Mercedes Ruehl"
)

