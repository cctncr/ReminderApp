package com.example.reminderapp.domain.repository

import com.example.reminderapp.domain.models.Reminder
import kotlinx.coroutines.flow.Flow

interface ReminderRepository {
    fun getAllReminders(): Flow<List<Reminder>>
    suspend fun getReminderById(id: String): Reminder?
    suspend fun insertReminder(reminder: Reminder)
    suspend fun updateReminder(reminder: Reminder)
    suspend fun deleteReminder(reminder: Reminder)
    suspend fun deleteReminderById(id: String)
    suspend fun updateReminderEnabled(id: String, enabled: Boolean)
}