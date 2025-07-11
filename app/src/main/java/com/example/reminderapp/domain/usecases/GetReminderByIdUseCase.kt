package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class GetReminderByIdUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String): Reminder? {
        return repository.getReminderById(reminderId)
    }
}