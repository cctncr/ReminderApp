package com.example.reminderapp.presentation.screens.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.domain.models.Reminder
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.domain.repository.ReminderRepository
import com.example.reminderapp.presentation.screens.main.models.ReminderUiState
import com.example.reminderapp.utils.ReminderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderRepository: ReminderRepository
) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiState>> = reminderRepository.getAllReminders()
        .map { reminders ->
            reminders.map { reminder ->
                ReminderUiState(
                    id = reminder.id,
                    title = reminder.title,
                    time = ReminderUtils.timeToString(reminder.time),
                    reminderType = reminder.reminderType,
                    isEnabled = reminder.isEnabled
                )
            }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    init {
        viewModelScope.launch {
            if (reminders.value.isEmpty()) {
                val sampleReminders = listOf(
                    Reminder(
                        id = UUID.randomUUID().toString(),
                        title = "Sabah Alarmı",
                        time = Time(8, 0),
                        isEnabled = true,
                        reminderType = ReminderType.ONE_TIME
                    ),
                    Reminder(
                        id = UUID.randomUUID().toString(),
                        title = "Öğle Molası",
                        time = Time(12, 30),
                        isEnabled = false,
                        reminderType = ReminderType.ONE_TIME
                    ),
                    Reminder(
                        id = UUID.randomUUID().toString(),
                        title = "Akşam Sporu",
                        time = Time(18, 45),
                        isEnabled = true,
                        reminderType = ReminderType.DAILY
                    )
                )
                sampleReminders.forEach { reminder ->
                    reminderRepository.insertReminder(reminder)
                }
            }
        }
    }

    fun createReminder(reminderUiState: ReminderUiState) {
        viewModelScope.launch {
            val reminder = Reminder(
                id = reminderUiState.id,
                title = reminderUiState.title,
                time = ReminderUtils.stringToTime(reminderUiState.time),
                reminderType = reminderUiState.reminderType,
                isEnabled = reminderUiState.isEnabled
            )
            reminderRepository.insertReminder(reminder)
        }
    }

    fun updateReminder(updatedReminder: ReminderUiState) {
        viewModelScope.launch {
            val reminder = Reminder(
                id = updatedReminder.id,
                title = updatedReminder.title,
                time = ReminderUtils.stringToTime(updatedReminder.time),
                reminderType = updatedReminder.reminderType,
                isEnabled = updatedReminder.isEnabled
            )
            reminderRepository.updateReminder(reminder)
        }
    }

    fun toggleEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            reminderRepository.updateReminderEnabled(id, enabled)
        }
    }

    fun updateTime(id: String, newTime: String) {
        viewModelScope.launch {
            val reminder = reminderRepository.getReminderById(id)
            reminder?.let {
                val updatedReminder = it.copy(time = ReminderUtils.stringToTime(newTime))
                reminderRepository.updateReminder(updatedReminder)
            }
        }
    }

    fun updateType(id: String, newType: ReminderType) {
        viewModelScope.launch {
            val reminder = reminderRepository.getReminderById(id)
            reminder?.let {
                val updatedReminder = it.copy(reminderType = newType)
                reminderRepository.updateReminder(updatedReminder)
            }
        }
    }

    fun deleteReminder(id: String) {
        viewModelScope.launch {
            reminderRepository.deleteReminderById(id)
        }
    }
}