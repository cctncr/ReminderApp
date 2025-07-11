package com.example.reminderapp.presentation.mapper

import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.presentation.models.ReminderUiState
import com.example.reminderapp.utils.ReminderUtils
import javax.inject.Inject

class ReminderUiMapper @Inject constructor() {

    fun toUiState(reminder: Reminder): ReminderUiState {
        return ReminderUiState(
            id = reminder.id,
            title = reminder.title,
            time = ReminderUtils.timeToString(reminder.time),
            reminderType = reminder.reminderType,
            isEnabled = reminder.isEnabled
        )
    }

    fun toDomain(uiState: ReminderUiState): Reminder {
        return Reminder(
            id = uiState.id,
            title = uiState.title,
            time = ReminderUtils.stringToTime(uiState.time),
            reminderType = uiState.reminderType,
            isEnabled = uiState.isEnabled
        )
    }
}