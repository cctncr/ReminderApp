package com.example.reminderapp.domain.usecases

import com.example.reminderapp.domain.repository.ReminderRepository
import javax.inject.Inject

class ToggleReminderEnabledUseCase @Inject constructor(
    private val repository: ReminderRepository
) {
    suspend operator fun invoke(reminderId: String, enabled: Boolean) {
        repository.updateReminderEnabled(reminderId, enabled)
    }
}