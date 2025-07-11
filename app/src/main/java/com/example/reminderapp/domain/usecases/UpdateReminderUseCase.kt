package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class UpdateReminderUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminder: Reminder) {
        repository.updateReminder(reminder)
    }
}