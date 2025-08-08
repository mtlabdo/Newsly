package com.instantsystem.newsly.common.core.util

import com.google.common.truth.Truth.assertThat
import com.instantsystem.common.core.util.DateTimeUtils
import org.junit.Test
import java.time.ZonedDateTime
import java.util.Locale

class DateTimeUtilsTest {

    @Test
    fun `should format valid ISO datetime to date`() {
        val isoDateTime = "2025-08-03T12:00:00Z"

        val result = DateTimeUtils.formatToIsoDate(isoDateTime)

        assertThat(result).isEqualTo("2025-08-03")
    }

    @Test
    fun `should format ISO datetime with timezone offset`() {
        val isoDateTime = "2025-08-03T14:30:00+02:00"

        val result = DateTimeUtils.formatToIsoDate(isoDateTime)

        assertThat(result).isEqualTo("2025-08-03")
    }

    @Test
    fun `should handle invalid datetime string`() {
        val invalidDateTime = "invalid-datetime"

        val result = DateTimeUtils.formatToIsoDate(invalidDateTime)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle empty datetime string`() {
        val emptyDateTime = ""

        val result = DateTimeUtils.formatToIsoDate(emptyDateTime)

        assertThat(result).isEmpty()
    }

    @Test
    fun `should format ZonedDateTime with custom pattern`() {
        val zonedDateTime = ZonedDateTime.parse("2025-08-03T12:00:00Z")

        val result = DateTimeUtils.formatDateTime(zonedDateTime, "dd/MM/yyyy")

        assertThat(result).isEqualTo("03/08/2025")
    }

    @Test
    fun `should format ZonedDateTime with time pattern`() {
        val zonedDateTime = ZonedDateTime.parse("2025-08-03T14:30:45Z")

        val result = DateTimeUtils.formatDateTime(zonedDateTime, "HH:mm:ss")

        assertThat(result).matches("\\d{2}:\\d{2}:\\d{2}")
    }

    @Test
    fun `should format ZonedDateTime with locale`() {
        val zonedDateTime = ZonedDateTime.parse("2025-08-03T12:00:00Z")

        val result = DateTimeUtils.formatDateTime(
            zonedDateTime,
            "EEEE, dd MMMM yyyy",
            Locale.FRANCE
        )

        assertThat(result).contains("août")
        assertThat(result).contains("2025")
    }

    @Test
    fun `should handle null ZonedDateTime`() {
        val result = DateTimeUtils.formatDateTime(null, "yyyy-MM-dd")

        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle invalid pattern gracefully`() {
        val zonedDateTime = ZonedDateTime.parse("2025-08-03T12:00:00Z")

        val result = DateTimeUtils.formatDateTime(zonedDateTime, "invalid-pattern-xyz")

        assertThat(result).isEmpty()
    }

    @Test
    fun `should handle different ISO formats`() {
        val formats = listOf(
            "2025-08-03T12:00:00Z",
            "2025-08-03T12:00:00+00:00",
            "2025-08-03T14:00:00+02:00",
            "2025-08-03T10:00:00-02:00"
        )

        formats.forEach { format ->
            val result = DateTimeUtils.formatToIsoDate(format)
            assertThat(result).isEqualTo("2025-08-03") // Always same date after normalizing
        }
    }
}
