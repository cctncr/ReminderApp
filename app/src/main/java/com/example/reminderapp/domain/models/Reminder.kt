package com.example.reminderapp.domain.models

data class Reminder(
    val id: String,
    val title: String?,
    val time: Time,
    val reminderType: ReminderType,
    val isEnabled: Boolean
)