package com.saswat10.network

import android.annotation.SuppressLint
import java.time.Instant
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit

@SuppressLint("NewApi")
fun convertToRelativeDate(utcDateTime: String): String {
    // Parse the UTC DateTime string
    val formatter = DateTimeFormatter.ISO_INSTANT
    val parsedDateTime = Instant.parse(utcDateTime)

    // Get the current time in UTC
    val currentDateTime = Instant.now()

    // Calculate the difference in days
    val daysDifference = ChronoUnit.DAYS.between(parsedDateTime, currentDateTime)

    return when {
        daysDifference == 0L -> "today"
        daysDifference == 1L -> "yesterday"
        daysDifference > 1 -> "$daysDifference days ago"
        else -> "In the future" // For dates in the future
    }
}
