package com.example.reminderapp.utils

import com.example.reminderapp.domain.models.Time
import java.util.Calendar

object ReminderUtils {

    fun timeToString(time: Time): String {
        return time.toString()
    }

    fun stringToTime(timeString: String): Time {
        val parts = timeString.split(":")
        val hour = parts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = parts.getOrNull(1)?.toIntOrNull() ?: 0
        return Time(hour, minute)
    }

    fun timeToMinutes(time: Time): Int {
        return time.hour * 60 + time.minute
    }

    fun getCurrentTime(): Time {
        val now = Calendar.getInstance()
        return Time(
            hour = now.get(Calendar.HOUR_OF_DAY),
            minute = now.get(Calendar.MINUTE)
        )
    }

    fun getTimeDifferenceInMinutes(time1: Time, time2: Time): Int {
        return timeToMinutes(time1) - timeToMinutes(time2)
    }
}