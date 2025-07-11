package com.example.reminderapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.presentation.screens.main.models.ReminderUiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ReminderViewModel : ViewModel() {
    private val _reminders = MutableStateFlow<List<ReminderUiState>>(emptyList())
    val reminders: StateFlow<List<ReminderUiState>> = _reminders.asStateFlow()

    init {
        _reminders.value = listOf(
            ReminderUiState(
                title = "Sabah Alarmı",
                time = "08:00",
                isEnabled = true,
                reminderType = ReminderType.ONE_TIME
            ),
            ReminderUiState(
                title = "Öğle Molası",
                time = "12:30",
                isEnabled = false,
                reminderType = ReminderType.ONE_TIME
            ),
            ReminderUiState(
                title = "Akşam Sporu",
                time = "18:45",
                isEnabled = true,
                reminderType = ReminderType.DAILY
            )
        )
    }

    fun createReminder(reminderUiState: ReminderUiState) {
        viewModelScope.launch {
            val newReminder = ReminderUiState(
                id = reminderUiState.id,
                title = reminderUiState.title,
                time = reminderUiState.time,
                isEnabled = reminderUiState.isEnabled,
                reminderType = reminderUiState.reminderType
            )
            _reminders.update { currentList ->
                currentList + newReminder
            }
        }

    }

    fun toggleEnabled(id: String, enabled: Boolean) {
        _reminders.update { list ->
            list.map { if (it.id == id) it.copy(isEnabled = enabled) else it }
        }
    }

    fun updateTime(id: String, newTime: String) {
        _reminders.update { list ->
            list.map { if (it.id == id) it.copy(time = newTime) else it }
        }
    }

    fun updateType(id: String, newType: ReminderType) {
        _reminders.update { list ->
            list.map { if (it.id == id) it.copy(reminderType = newType) else it }
        }
    }
}

