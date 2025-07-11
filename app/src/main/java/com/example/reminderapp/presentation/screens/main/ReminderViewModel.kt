package com.example.reminderapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.usecases.ReminderUseCases
import com.example.reminderapp.presentation.screens.main.models.ReminderUiState
import com.example.reminderapp.utils.ReminderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderUseCases: ReminderUseCases
) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiState>> = reminderUseCases.getAllReminders()
        .map { reminders ->
            reminders.map { reminder -> reminder.toUiState() }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun createReminder(reminderUiState: ReminderUiState) {
        viewModelScope.launch {
            reminderUseCases.createReminder(reminderUiState.toDomainModel())
        }
    }

    fun updateReminder(updatedReminder: ReminderUiState) {
        viewModelScope.launch {
            reminderUseCases.updateReminder(updatedReminder.toDomainModel())
        }
    }

    fun toggleEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            reminderUseCases.toggleReminderEnabled(id, enabled)
        }
    }

    fun updateTime(id: String, newTime: String) {
        viewModelScope.launch {
            reminderUseCases.getReminderById(id)?.let { reminder ->
                val updatedReminder = reminder.copy(time = ReminderUtils.stringToTime(newTime))
                reminderUseCases.updateReminder(updatedReminder)
            }
        }
    }

    fun updateType(id: String, newType: ReminderType) {
        viewModelScope.launch {
            reminderUseCases.getReminderById(id)?.let { reminder ->
                val updatedReminder = reminder.copy(reminderType = newType)
                reminderUseCases.updateReminder(updatedReminder)
            }
        }
    }

    fun deleteReminder(id: String) {
        viewModelScope.launch {
            reminderUseCases.deleteReminder(id)
        }
    }
}

private fun Reminder.toUiState(): ReminderUiState {
    return ReminderUiState(
        id = id,
        title = title,
        time = ReminderUtils.timeToString(time),
        reminderType = reminderType,
        isEnabled = isEnabled
    )
}

private fun ReminderUiState.toDomainModel(): Reminder {
    return Reminder(
        id = id,
        title = title,
        time = ReminderUtils.stringToTime(time),
        reminderType = reminderType,
        isEnabled = isEnabled
    )
}