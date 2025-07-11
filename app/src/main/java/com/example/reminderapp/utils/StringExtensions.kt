package com.example.reminderapp.utils

fun String.toTimeOrNull(): com.example.reminderapp.domain.models.Time? {
    return try {
        val parts = split(":")
        if (parts.size == 2) {
            com.example.reminderapp.domain.models.Time(
                hour = parts[0].toInt(),
                minute = parts[1].toInt()
            )
        } else null
    } catch (e: Exception) {
        null
    }
}