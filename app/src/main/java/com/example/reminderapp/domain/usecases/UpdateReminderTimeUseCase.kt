package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.repository.ReminderRepository
import com.example.reminderapp.utils.ReminderUtils
import javax.inject.Inject

class UpdateReminderTimeUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String, newTimeString: String) {
        repository.getReminderById(reminderId)?.let { reminder ->
            val updatedReminder = reminder.copy(
                time = ReminderUtils.stringToTime(newTimeString)
            )
            repository.updateReminder(updatedReminder)
        }
    }
}