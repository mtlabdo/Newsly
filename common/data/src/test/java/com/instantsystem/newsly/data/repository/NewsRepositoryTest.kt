package com.instantsystem.newsly.data.repository

import com.instantsystem.common.core.util.INetworkChecker
import com.instantsystem.newsly.common.remote.api.INewsApiService
import com.instantsystem.newsly.common.remote.model.ArticleDto
import com.instantsystem.newsly.common.remote.model.NewsResponse
import com.instantsystem.newsly.common.remote.model.SourceDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test
import com.instantsystem.newsly.domain.Result
import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import io.mockk.every
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain

@OptIn(ExperimentalCoroutinesApi::class)
class NewsRepositoryTest {

    private val api: INewsApiService = mockk()
    private val networkChecker: INetworkChecker = mockk()
    private val testDispatcher = StandardTestDispatcher()

    private lateinit var repository: NewsRepository

    @Before
    fun setup() {
        Dispatchers.setMain(testDispatcher)

        repository = NewsRepository(
            remoteDataSource = api,
            networkChecker = networkChecker,
            ioDispatcher = testDispatcher
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `when network is available and api returns data, then articles are emitted`() = runTest {
        // Given
        coEvery { networkChecker.isNetworkAvailable() } returns true

        val articleDto = ArticleDto(
            title = "Test Title",
            description = "Desc",
            url = "https://test.com",
            urlToImage = "https://image.com",
            publishedAt = "2025-08-06",
            source = SourceDto(name = "Test Source"),
            author = "Author"
        )
        val response = NewsResponse(articles = listOf(articleDto), status = "OK", totalResults = 1)

        coEvery { api.getTopHeadlines(any()) } returns response

        // When
        val results = repository.getTopHeadlines("en").toList()

        // Then
        val successResult = results.find { it is Result.Success } as Result.Success
        val articles = successResult.data

        assertThat(articles).hasSize(1)
        assertThat(articles.first().title).isEqualTo("Test Title")
    }

    @Test
    fun `when network is unavailable, then emits Loading then Error with NewsException`() = runTest {
        every { networkChecker.isNetworkAvailable() } returns false
        val results = repository.getTopHeadlines("en").toList()

        assertThat(results).hasSize(2)

        //Vérifie que le premier item est Loading
        assertThat(results[0]).isEqualTo(Result.Loading)

        // Vérifie que le second est un Result.Error
        val error = results[1]
        assertThat(error).isInstanceOf(Result.Error::class.java)

        // Vérifie que l'excep est NETWORK
        val exception = (error as Result.Error).exception
        assertThat(exception).isInstanceOf(NewsException::class.java)
        assertThat((exception as NewsException).errorType).isEqualTo(NewsError.NETWORK)
    }
}