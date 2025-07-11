package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class UpdateReminderTypeUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String, newType: ReminderType) {
        repository.getReminderById(reminderId)?.let { reminder ->
            val updatedReminder = reminder.copy(reminderType = newType)
            repository.updateReminder(updatedReminder)
        }
    }
}