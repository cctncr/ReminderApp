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
            _reminders.update { currentList ->
                currentList + reminderUiState
            }
        }
    }

    fun updateReminder(updatedReminder: ReminderUiState) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.map { reminder ->
                    if (reminder.id == updatedReminder.id) {
                        updatedReminder
                    } else {
                        reminder
                    }
                }
            }
        }
    }

    fun toggleEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.map { reminder ->
                    if (reminder.id == id) {
                        reminder.copy(isEnabled = enabled)
                    } else {
                        reminder
                    }
                }
            }
        }
    }

    fun updateTime(id: String, newTime: String) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.map { reminder ->
                    if (reminder.id == id) {
                        reminder.copy(time = newTime)
                    } else {
                        reminder
                    }
                }
            }
        }
    }

    fun updateType(id: String, newType: ReminderType) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.map { reminder ->
                    if (reminder.id == id) {
                        reminder.copy(reminderType = newType)
                    } else {
                        reminder
                    }
                }
            }
        }
    }

    fun deleteReminder(id: String) {
        viewModelScope.launch {
            _reminders.update { currentList ->
                currentList.filter { it.id != id }
            }
        }
    }
}