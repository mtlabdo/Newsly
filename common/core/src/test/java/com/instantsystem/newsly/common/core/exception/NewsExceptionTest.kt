package com.instantsystem.newsly.common.core.exception

import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.exception.NewsError
import com.instantsystem.common.core.exception.NewsException
import org.junit.Test

class NewsExceptionTest {


    @Test
    fun `should create NewsException with all parameters`() {
        val exception = NewsException(
            errorType = NewsError.SERVER,
            serverCode = 500,
            message = "Internal server error"
        )

        assertThat(exception.errorType).isEqualTo(NewsError.SERVER)
        assertThat(exception.serverCode).isEqualTo(500)
        assertThat(exception.message).isEqualTo("Internal server error")
    }

    @Test
    fun `should be throwable exception`() {
        val exception = NewsException(NewsError.NETWORK)

        assertThat(exception).isNotNull()
        assertThat(exception).isInstanceOf(Exception::class.java)
        assertThat(exception).isInstanceOf(Throwable::class.java)
    }
}
