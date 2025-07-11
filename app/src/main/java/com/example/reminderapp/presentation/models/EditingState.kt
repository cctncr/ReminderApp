package com.example.reminderapp.presentation.models

data class EditingState(
    val reminder: ReminderUiState,
    val isEditMode: Boolean
)