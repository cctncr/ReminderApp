package com.example.reminderapp.presentation.models

import com.example.reminderapp.domain.models.ReminderType
import java.util.UUID

data class ReminderUiState(
    val id: String = UUID.randomUUID().toString(),
    val title: String?,
    val time: String,
    val reminderType: ReminderType,
    val isEnabled: Boolean
)