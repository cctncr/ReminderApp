package com.example.reminderapp.domain.models

data class Reminder(
    val id: Long = 0,
    val title: String? = null,
    val time: Time,
    val reminderType: ReminderType,
    val isEnabled: Boolean = true,
)