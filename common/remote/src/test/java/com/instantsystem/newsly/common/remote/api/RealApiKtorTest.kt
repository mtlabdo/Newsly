package com.instantsystem.newsly.common.remote.api

import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.ConfigConstants
import com.instantsystem.newsly.common.remote.ktor.KtorClient
import io.ktor.client.HttpClient
import kotlinx.coroutines.test.runTest
import org.junit.Assume
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import java.util.Properties

class RealApiIntegrationTest : KoinTest {

    private val realApiKey = getApiKeyFromLocalProperties()

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val testModule = module {
        single(ConfigConstants.newsApiNamedKey) { realApiKey }
        single(ConfigConstants.debugModeNamedKey) { false }
        single<HttpClient> { KtorClient.create() }
        single<INewsApiService> {
            NewsApiService(
                client = get(),
                apiKey = get(ConfigConstants.newsApiNamedKey)
            )
        }
    }

    private val newsApiService: INewsApiService by inject()

    @Before
    fun setUp() {
        Assume.assumeTrue("API key must not be empty", realApiKey.isNotBlank())
        Assume.assumeTrue("API key must be valid", realApiKey != "####NO_API_KEY####")
    }

    @Test
    fun `should fetch real top headlines successfully`() = runTest {
        val result = newsApiService.getTopHeadlines("en")

        assertThat(result.status).isEqualTo("ok")
        assertThat(result.totalResults).isGreaterThan(0)
        assertThat(result.articles).isNotEmpty()

        val firstArticle = result.articles[0]
        assertThat(firstArticle.title).isNotNull()
        assertThat(firstArticle.url).isNotNull()
        assertThat(firstArticle.publishedAt).isNotNull()
        assertThat(firstArticle.source.name).isNotNull()

        println("Fetched ${result.articles.size} articles")
    }

    @Test
    fun `should handle different languages`() = runTest {
        val englishResult = newsApiService.getTopHeadlines("en")
        val frenchResult = newsApiService.getTopHeadlines("fr")

        assertThat(englishResult.status).isEqualTo("ok")
        assertThat(frenchResult.status).isEqualTo("ok")
    }
}

private fun getApiKeyFromLocalProperties(): String {
    return try {
        val localProperties = Properties()
        val possiblePaths = listOf("local.properties", "../local.properties", "../../local.properties")

        var localPropertiesFile: java.io.File? = null
        for (path in possiblePaths) {
            val file = java.io.File(path)
            if (file.exists()) {
                localPropertiesFile = file
                break
            }
        }

        if (localPropertiesFile?.exists() == true) {
            localPropertiesFile.inputStream().use { inputStream ->
                localProperties.load(inputStream)
            }
            localProperties.getProperty("API_KEY") ?: "####NO_API_KEY####"
        } else {
            System.getenv("API_KEY") ?: System.getProperty("API_KEY") ?: "####NO_API_KEY####"
        }
    } catch (e: Exception) {
        "####NO_API_KEY####"
    }
}