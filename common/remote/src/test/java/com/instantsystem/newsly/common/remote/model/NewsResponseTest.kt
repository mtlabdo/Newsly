package com.instantsystem.newsly.common.remote.model

import kotlinx.serialization.json.Json
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class NewsResponseTest {
    private val json = Json {
        ignoreUnknownKeys = true
        explicitNulls = true
        isLenient = true
        encodeDefaults = true
        classDiscriminator = "status"
    }


    @Test
    fun `should serialize and deserialize NewsResponse correctly`() {
        // Given
        val newsResponse = NewsResponse(
            status = "ok",
            totalResults = 1,
            articles = listOf(exampleArticleDto)
        )

        val serialized = json.encodeToString(NewsResponse.serializer(), newsResponse)
        val deserialized = json.decodeFromString(NewsResponse.serializer(), serialized)

        assertEquals(newsResponse.status, deserialized.status)
        assertEquals(newsResponse.totalResults, deserialized.totalResults)
        assertEquals(newsResponse.articles.size, deserialized.articles.size)
        assertEquals(newsResponse.articles[0].title, deserialized.articles[0].title)
    }

    @Test
    fun `should handle articles with minimal required fields`() {

        val minimalArticleJson = """
        {
            "title": "‘Knives out’: Switzerland descends",
            "url": "https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b",
            "publishedAt": "2025-08-03T11:57:02Z",
            "source": {
                "name": "Financial Times"
            }
        }
        """.trimIndent()

        // When
        val result = json.decodeFromString<ArticleDto>(minimalArticleJson)

        // Then
        assertEquals("‘Knives out’: Switzerland descends", result.title)
        assertEquals("https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b", result.url)
        assertEquals("2025-08-03T11:57:02Z", result.publishedAt)
        assertEquals("Financial Times", result.source.name)
        assertNull(result.description)
        assertNull(result.urlToImage)
        assertNull(result.author)
        assertNull(result.content)
        assertNull(result.source.id)
    }
}

private val exampleArticleDto = ArticleDto(
    title = "‘Knives out’: Switzerland descends into blame game after US tariff shock - Financial Times",
    description = "President Karin Keller-Sutter under fire for failed trade talks with Trump administration",
    url = "https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b",
    urlToImage = "https://www.ft.com/__origami/service/image/v2/images/raw/https%3A%2F%2Fd1e00ek4ebabms.cloudfront.net%2Fproduction%2Fd38fc802-cfd3-4a0f-bf95-bc59ab93e11d.jpg?source=next-barrier-page",
    publishedAt = "2025-08-03T11:57:02Z",
    source = SourceDto(id = "null", name = "Financial Times"),
    author = "Mercedes Ruehl",
    content = "Complete digital access to quality analysis and expert insights, complemented with our award-winning Weekend Print edition.\\r\\n<ul><li></li>Everything in Print<li></li>Weekday Print Edition<li></li>FT … [+202 chars]"
)
