package com.example.mfinance.util

import java.text.SimpleDateFormat
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Calendar
import java.util.Locale

fun getFormattedDate(timeInMillis: Long): String {
    val calender = Calendar.getInstance()
    calender.timeInMillis = timeInMillis
    val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.US)
    return dateFormat.format(calender.timeInMillis)
}

fun LocalDateTime.toMilli() = this.toInstant(ZoneOffset.UTC).toEpochMilli()

fun getFirstAndLastDayOfCurrentMonth(): Pair<Long, Long> {
    val calendar = Calendar.getInstance()

    calendar.set(Calendar.DAY_OF_MONTH, 1)
    val firstDayInMillis = calendar.timeInMillis

    calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH))
    val lastDayInMillis = calendar.timeInMillis

    return Pair(firstDayInMillis, lastDayInMillis)
}

fun getLastDayOfCurrentMonth():Int{
    val calendar = Calendar.getInstance()
    return calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
}

fun Long.toLocalDateTime(): LocalDateTime =
    LocalDateTime.ofInstant(Instant.ofEpochMilli(this), ZoneOffset.UTC)