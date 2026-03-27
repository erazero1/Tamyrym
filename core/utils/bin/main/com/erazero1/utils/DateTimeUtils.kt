package com.erazero1.utils

import java.time.Instant
import java.time.LocalDate
import java.time.Period
import java.time.ZoneId
import java.time.format.DateTimeFormatter

fun String?.toInstantOrNull(
): Instant? {
    return try {
        this?.takeIf { dateTime -> dateTime.isNotBlank() }
            ?.let { dateTime -> Instant.parse(dateTime) }
    } catch (_: Exception) {
        null
    }
}


enum class DateFormatStyle(val pattern: String) {
    HH_MM("HH:mm"),

    DD_MM("dd.MM"),

    DD_MM_YYYY("dd.MM.yyyy"),

    DD_MMMM_YYYY("dd MMMM yyyy"),

    YYYY_MM_DD("yyyy-MM-dd"),

    DD_MM_YYYY_HH_MM("dd.MM.yyyy HH:mm"),

    DD_MM_HH_MM("dd MMMM, HH:mm"),

    FULL("dd MMMM yyyy, HH:mm");
}

fun Instant?.format(
    dateFormat: DateFormatStyle = DateFormatStyle.DD_MM_YYYY,
    zoneId: ZoneId = ZoneId.systemDefault(),
): String? {
    return this
        ?.atZone(zoneId)
        ?.format(DateTimeFormatter.ofPattern(dateFormat.pattern))
}

fun Instant?.calculateAge(): Int? {
    if (this == null) return null
    val defaultZone = ZoneId.systemDefault()
    val birthDate = this.atZone(defaultZone).toLocalDate()
    val today = LocalDate.now(defaultZone)
    return Period.between(birthDate, today).years
}