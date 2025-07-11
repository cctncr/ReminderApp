package com.example.reminderapp.domain.usecases

data class ReminderUseCases(
    val getAllReminders: GetAllRemindersUseCase,
    val getReminderById: GetReminderByIdUseCase,
    val createReminder: CreateReminderUseCase,
    val updateReminder: UpdateReminderUseCase,
    val deleteReminder: DeleteReminderUseCase,
    val toggleReminderEnabled: ToggleReminderEnabledUseCase
)