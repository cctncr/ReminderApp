package com.example.reminderapp.utils

object ReminderConstants {
    const val MAX_TITLE_LENGTH = 24
    const val DATABASE_NAME = "reminder_database"
    const val TABLE_NAME = "reminders"

    // Time formatting
    const val TIME_FORMAT_PATTERN = "HH:mm"
    const val TIME_SEPARATOR = ":"

    // UI Constants
    const val REMINDER_TYPE_ONE_TIME_LABEL = "One Time"
    const val REMINDER_TYPE_DAILY_LABEL = "Daily"

    // Navigation
    const val REMINDER_ID_ARG = "reminderId"

    // StateFlow
    const val STOP_TIMEOUT_MILLIS = 5000L
}