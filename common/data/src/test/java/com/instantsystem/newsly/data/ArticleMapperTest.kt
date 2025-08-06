package com.instantsystem.newsly.data

import com.instantsystem.newsly.common.remote.model.ArticleDto
import com.instantsystem.newsly.common.remote.model.SourceDto
import com.instantsystem.newsly.data.mapper.toDomainModel
import com.instantsystem.newsly.domain.model.Article
import org.junit.Test
import com.google.common.truth.Truth.assertThat


class ArticleMapperTest {

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

    @Test
    fun `toDomainModel maps all fields correctly from example DTO`() {
        val articleDto = exampleArticleDto

        val domainArticle = articleDto.toDomainModel()

        assertThat(domainArticle.title).isEqualTo("‘Knives out’: Switzerland descends into blame game after US tariff shock - Financial Times")
        assertThat(domainArticle.description).isEqualTo("President Karin Keller-Sutter under fire for failed trade talks with Trump administration")
        assertThat(domainArticle.url).isEqualTo("https://www.ft.com/content/6804c076-4961-4373-a4f8-1e59ab5d7f8b")
        assertThat(domainArticle.imageUrl).isEqualTo("https://www.ft.com/__origami/service/image/v2/images/raw/https%3A%2F%2Fd1e00ek4ebabms.cloudfront.net%2Fproduction%2Fd38fc802-cfd3-4a0f-bf95-bc59ab93e11d.jpg?source=next-barrier-page")
        assertThat(domainArticle.publishedAt).isEqualTo("2025-08-03T11:57:02Z")
        assertThat(domainArticle.sourceName).isEqualTo("Financial Times")
        assertThat(domainArticle.author).isEqualTo("Mercedes Ruehl")

        val expectedId = Article.generateId(articleDto.title, articleDto.publishedAt)
        assertThat(domainArticle.id).isEqualTo(expectedId)
    }

    @Test
    fun `toDomainModel handles null description correctly with example DTO`() {
        val articleDtoWithNullDesc = exampleArticleDto.copy(description = null)
        val domainArticle = articleDtoWithNullDesc.toDomainModel()
        assertThat(domainArticle.description).isEmpty()
    }
}