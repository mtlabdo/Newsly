package com.instantsystem.common.core.util

import java.time.OffsetDateTime
import java.time.ZoneId
import java.time.ZonedDateTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale


object DateTimeUtils {

    private val INPUT_FORMATTER = DateTimeFormatter.ISO_OFFSET_DATE_TIME

    private fun parseIsoString(datetime: String): ZonedDateTime? {
        return try {
            val offsetDateTime = OffsetDateTime.parse(datetime, INPUT_FORMATTER)
            offsetDateTime.atZoneSameInstant(ZoneId.systemDefault())
        } catch (e: DateTimeParseException) {
            println("Erreur de formatage -> $datetime - ${e.message}")
            null
        }
    }

    fun formatDateTime(
        zonedDateTime: ZonedDateTime?,
        pattern: String,
        locale: Locale = Locale.getDefault()
    ): String {
        return zonedDateTime?.let {
            try {
                val formatter = DateTimeFormatter.ofPattern(pattern, locale)
                it.format(formatter)
            } catch (e: Exception) {
                println("Erreur de formatage -> $pattern: ${e.message}")
                ""
            }
        } ?: ""
    }

    fun formatToIsoDate(datetime: String): String {
        val zonedDateTime = parseIsoString(datetime)
        return formatDateTime(zonedDateTime, "yyyy-MM-dd")
    }
}