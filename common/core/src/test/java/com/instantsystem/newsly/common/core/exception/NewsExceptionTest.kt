package com.instantsystem.newsly.common.core.exception

import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull

class NewsExceptionTest {

    @Test
    fun `should create NewsException with error type only`() {
        val exception = NewsException(NewsError.AUTH)

        assertEquals(NewsError.AUTH, exception.errorType)
        assertEquals("AUTH", exception.message)
        assertEquals(null, exception.serverCode)
    }

    @Test
    fun `should create NewsException with all parameters`() {
        val exception = NewsException(
            errorType = NewsError.SERVER,
            serverCode = 500,
            message = "Internal server error"
        )

        assertEquals(NewsError.SERVER, exception.errorType)
        assertEquals(500, exception.serverCode)
        assertEquals("Internal server error", exception.message)
    }

    @Test
    fun `should be throwable exception`() {
        val exception = NewsException(NewsError.NETWORK)

        assertNotNull(exception)
        assert(exception is Exception)
        assert(exception is Throwable)
    }
}