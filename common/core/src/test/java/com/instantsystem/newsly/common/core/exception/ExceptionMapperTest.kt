package com.instantsystem.newsly.common.core.exception

import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.toNewsException
import org.junit.Test
import java.net.SocketTimeoutException
import java.net.UnknownHostException
import kotlin.test.assertEquals

class ExceptionMapperTest {

    @Test
    fun `should map UnknownHostException to NETWORK error`() {
        val exception = UnknownHostException()

        val newsException = exception.toNewsException()

        assertEquals(NewsError.NETWORK, newsException.errorType)
    }

    @Test
    fun `should map SocketTimeoutException to NETWORK error`() {
        val exception = SocketTimeoutException()

        val newsException = exception.toNewsException()

        assertEquals(NewsError.NETWORK, newsException.errorType)
    }

    @Test
    fun `should map other exceptions to UNKNOWN error`() {
        val exception = RuntimeException("Unexpected error")

        val newsException = exception.toNewsException()

        assertEquals(NewsError.UNKNOWN, newsException.errorType)
    }

    @Test
    fun `should map IllegalArgumentException to UNKNOWN error`() {
        val exception = IllegalArgumentException()

        val newsException = exception.toNewsException()

        assertEquals(NewsError.UNKNOWN, newsException.errorType)
    }

    @Test
    fun `should preserve error message in mapped exception`() {
        val originalException = RuntimeException("Original error")

        val newsException = originalException.toNewsException()

        assertEquals("UNKNOWN", newsException.message)
        assertEquals(NewsError.UNKNOWN, newsException.errorType)
    }
}
