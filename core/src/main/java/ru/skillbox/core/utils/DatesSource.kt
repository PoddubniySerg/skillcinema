package ru.skillbox.core.utils

import java.time.LocalDate
import java.time.format.DateTimeFormatter

object DatesSource {

    private const val DATE_FORMAT = "yyyy-MM-dd"
    private const val MAX_DAYS_PREMIERS = 14L

    fun getDateFrom(): LocalDate {
        return LocalDate.now()
    }

    fun getDateTo(dateFrom: LocalDate): LocalDate {
        return LocalDate.from(dateFrom.plusDays(MAX_DAYS_PREMIERS))
    }

    fun getDateFormatter(): DateTimeFormatter {
        return DateTimeFormatter.ofPattern(DATE_FORMAT)
    }
}