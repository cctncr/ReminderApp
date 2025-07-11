package com.example.reminderapp.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "reminders")
data class ReminderEntity(
    @PrimaryKey
    val id: String,
    val title: String?,
    val hour: Int,
    val minute: Int,
    val reminderType: String,
    val isEnabled: Boolean,
    val sortOrder: Int
)