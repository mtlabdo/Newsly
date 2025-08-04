package com.instantsystem.newsly.common.remote.api

import com.instantsystem.common.core.ConfigConstants
import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import com.instantsystem.newsly.common.remote.ktor.KtorClient
import io.ktor.client.HttpClient
import io.ktor.client.engine.mock.MockEngine
import io.ktor.client.engine.mock.respond
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.http.headersOf
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Rule
import org.junit.Test
import org.koin.dsl.module
import org.koin.test.KoinTest
import org.koin.test.KoinTestRule
import org.koin.test.inject
import kotlin.test.assertEquals
import kotlin.test.fail

class NewsApiServiceKoinTestUtilities : KoinTest {

    private val testApiKey = "test-api-key"

    @get:Rule
    val koinTestRule = KoinTestRule.create {
        modules(testModule)
    }

    private val testModule = module {
        single<HttpClient> {
            createTestKtorClient()
        }
        single(ConfigConstants.debugModeNamedKey) { true }
        single(ConfigConstants.newsApiNamedKey) { testApiKey }

        single<INewsApiService> {
            NewsApiService(
                client = get(),
                apiKey = get(ConfigConstants.newsApiNamedKey)
            )
        }
    }
    private val newsApiService: INewsApiService by inject()


    @Test
    fun `should validate module configuration`() {
        val debugMode: Boolean by inject(ConfigConstants.debugModeNamedKey)
        val apiKey: String by inject(ConfigConstants.newsApiNamedKey)
        assertEquals(true, debugMode)
        assertEquals(testApiKey, apiKey)
    }

    @Test
    fun `getTopHeadlines should return successful response`() = runTest {
        // When
        val result = newsApiService.getTopHeadlines("en")

        // Then
        assertEquals("ok", result.status)
        assertEquals(2, result.totalResults)
        assertEquals(2, result.articles.size)
        assertEquals(
            "‘Knives out’: Switzerland descends into blame game after US tariff shock - Financial Times",
            result.articles[0].title
        )
        assertEquals("Financial Times", result.articles[0].source.name)
    }

    @Test
    fun `should handle service errors correctly`() = runTest {
        val errorClient = createErrorMockHttpClient()
        val errorService = NewsApiService(errorClient, testApiKey)
        try {
            errorService.getTopHeadlines("en")
            assert(false) { "Should have thrown NewsException" }
        } catch (e: NewsException) {
            assertEquals(NewsError.AUTH, e.errorType)
        }
    }

    @Test
    fun `should handle server errors (500)`() = runTest {
        val serverErrorClient = createMockClientWith500()
        val service = NewsApiService(serverErrorClient, apiKey = testApiKey)

        try {
            service.getTopHeadlines("en")
            fail("Should have thrown NewsException")
        } catch (e: NewsException) {
            assertEquals(NewsError.UNKNOWN, e.errorType)
        }

    }

    @Test
    fun `should handle 429 Too Many Requests and map to NewsError LIMIT`() = runTest {
        val serverErrorClient = createMockClientWith429()
        val service = NewsApiService(serverErrorClient, apiKey = testApiKey)

        try {
            service.getTopHeadlines("en")
            fail("Should have thrown NewsException")
        } catch (e: NewsException) {
            assertEquals(NewsError.LIMIT, e.errorType)
        }
    }
}

private fun createErrorMockHttpClient(): HttpClient {
    val mockEngine = MockEngine {
        respond(
            content = """{"status":"error","code":"apiKeyInvalid"}""",
            status = HttpStatusCode.Unauthorized,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    return HttpClient(mockEngine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}

private fun createMockClientWith500(): HttpClient {
    val mockEngine = MockEngine {
        respond(
            content = """{"status":"error","message":"Internal server error"}""",
            status = HttpStatusCode.InternalServerError,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    return HttpClient(mockEngine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}

private fun createMockClientWith429(): HttpClient {
    val mockEngine = MockEngine {
        respond(
            content = """{"status":"error","message":"limit server error"}""",
            status = HttpStatusCode.TooManyRequests,
            headers = headersOf(HttpHeaders.ContentType, "application/json")
        )
    }

    return HttpClient(mockEngine) {
        expectSuccess = true
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }
    }
}


private fun createTestKtorClient(): HttpClient {
    val mockEngine = MockEngine { request ->
        val apiKey = request.url.parameters["apiKey"]
        when {
            apiKey != "test-api-key" -> respond(
                content = """{"status":"error","code":"apiKeyInvalid"}""",
                status = HttpStatusCode.Unauthorized,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )

            else -> respond(
                content = successfulNewsResponse,
                status = HttpStatusCode.OK,
                headers = headersOf(HttpHeaders.ContentType, "application/json")
            )
        }
    }

    return HttpClient(mockEngine) {
        expectSuccess = true

        defaultRequest {
            contentType(ContentType.Application.Json)
        }

        install(HttpTimeout) {
            connectTimeoutMillis = KtorClient.TIMEOUT
            requestTimeoutMillis = KtorClient.TIMEOUT
        }
        install(ContentNegotiation) {
            json(Json {
                ignoreUnknownKeys = true
                explicitNulls = true
                isLenient = true
                encodeDefaults = true
                classDiscriminator = "status"
            })
        }
    }
}

val successfulNewsResponse = """
    {
    "status": "ok",
    "totalResults": 2,
    "articles": [
        {
            "source": {
                "id": null,
                "name": "Financial Times"
            },
            "author": "Mercedes Ruehl",
            "title": "‘Knives out’: Switzerland descends into blame game after US tariff shock - Financial Times",
            "description": "President Karin Keller-Sutter under fire for failed trade talks with Trump administration",
            "url": "https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b",
            "urlToImage": "https://www.ft.com/__origami/service/image/v2/images/raw/https%3A%2F%2Fd1e00ek4ebabms.cloudfront.net%2Fproduction%2Fd38fc802-cfd3-4a0f-bf95-bc59ab93e11d.jpg?source=next-barrier-page",
            "publishedAt": "2025-08-03T11:57:02Z",
            "content": "Complete digital access to quality analysis and expert insights, complemented with our award-winning Weekend Print edition.\r\n<ul><li></li>Everything in Print<li></li>Weekday Print Edition<li></li>FT … [+202 chars]"
        },
        {
            "source": {
                "id": null,
                "name": "Gizmodo.com"
            },
            "author": "Ece Yildirim",
            "title": "Silicon Valley’s AI Spend Goes Berserk as Microsoft Starts Cashing In - Gizmodo",
            "description": "Tech giants reported eye-watering figures linked to artificial intelligence investment.",
            "url": "https://gizmodo.com/silicon-valleys-ai-spend-goes-berserk-as-microsoft-starts-cashing-in-2000638318",
            "urlToImage": "https://gizmodo.com/app/uploads/2025/07/GettyImages-2153471300-1200x675.jpg",
            "publishedAt": "2025-08-03T10:00:27Z",
            "content": "Meta, Apple, Microsoft, and Amazon all reported quarterly earnings this week, and there was a common thread tying them together: a boom in AI spending and plans to increase it even more, beyond analy… [+4809 chars]"
        }
    ]
}
    """.trimIndent()