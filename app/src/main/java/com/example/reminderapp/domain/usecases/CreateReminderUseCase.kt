package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class CreateReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.insertReminder(reminder)
    }
}