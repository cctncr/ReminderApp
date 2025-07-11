package com.example.reminderapp.utils

import com.example.reminderapp.domain.models.Time
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

fun Time.toFormattedString(pattern: String = "HH:mm"): String {
    val calendar = Calendar.getInstance().apply {
        set(Calendar.HOUR_OF_DAY, hour)
        set(Calendar.MINUTE, minute)
    }
    return SimpleDateFormat(pattern, Locale.getDefault()).format(calendar.time)
}

fun Time.toMillis(): Long {
    return (hour * 60 + minute) * 60 * 1000L
}