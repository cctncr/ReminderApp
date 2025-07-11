package com.example.reminderapp.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.domain.usecases.ReminderUseCases
import com.example.reminderapp.presentation.mapper.ReminderUiMapper
import com.example.reminderapp.presentation.models.EditingState
import com.example.reminderapp.presentation.models.ReminderUiState
import com.example.reminderapp.utils.ReminderConstants
import com.example.reminderapp.utils.ReminderUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderUseCases: ReminderUseCases,
    private val uiMapper: ReminderUiMapper
) : ViewModel() {

    val reminders: StateFlow<List<ReminderUiState>> = reminderUseCases.getAllReminders()
        .map { reminders ->
            reminders.map { reminder -> uiMapper.toUiState(reminder) }
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    private val _editingState = MutableStateFlow<EditingState?>(null)
    val editingState: StateFlow<EditingState?> = _editingState.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    fun toggleEnabled(id: String, enabled: Boolean) {
        viewModelScope.launch {
            try {
                reminderUseCases.toggleReminderEnabled(id, enabled)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateTime(id: String, newTime: String) {
        viewModelScope.launch {
            try {
                reminderUseCases.updateReminderTime(id, newTime)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateType(id: String, newType: ReminderType) {
        viewModelScope.launch {
            try {
                reminderUseCases.updateReminderType(id, newType)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun deleteReminder(id: String) {
        viewModelScope.launch {
            try {
                reminderUseCases.deleteReminder(id)
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun startCreating() {
        _editingState.value = EditingState(
            reminder = ReminderUiState(
                id = UUID.randomUUID().toString(),
                title = null,
                time = ReminderUtils.timeToString(ReminderUtils.getCurrentTime()),
                reminderType = ReminderType.ONE_TIME,
                isEnabled = true
            ),
            isEditMode = false
        )
    }

    fun startEditing(reminderId: String) {
        viewModelScope.launch {
            try {
                reminderUseCases.getReminderById(reminderId)?.let { reminder ->
                    _editingState.value = EditingState(
                        reminder = uiMapper.toUiState(reminder),
                        isEditMode = true
                    )
                }
            } catch (e: Exception) {
                _error.value = e.message
            }
        }
    }

    fun updateEditingTitle(title: String) {
        _editingState.value?.let { state ->
            if (title.length <= ReminderConstants.MAX_TITLE_LENGTH) {
                _editingState.value = state.copy(
                    reminder = state.reminder.copy(title = title.ifBlank { null })
                )
            }
        }
    }

    fun updateEditingTime(time: Time) {
        _editingState.value?.let { state ->
            _editingState.value = state.copy(
                reminder = state.reminder.copy(
                    time = ReminderUtils.timeToString(time)
                )
            )
        }
    }

    fun updateEditingType(type: ReminderType) {
        _editingState.value?.let { state ->
            _editingState.value = state.copy(
                reminder = state.reminder.copy(reminderType = type)
            )
        }
    }

    fun saveReminder() {
        _editingState.value?.let { state ->
            viewModelScope.launch {
                _isLoading.value = true
                try {
                    val reminder = uiMapper.toDomain(state.reminder)
                    if (state.isEditMode) {
                        reminderUseCases.updateReminder(reminder)
                    } else {
                        reminderUseCases.createReminder(reminder)
                    }
                    clearEditingState()
                } catch (e: Exception) {
                    _error.value = e.message
                } finally {
                    _isLoading.value = false
                }
            }
        }
    }

    fun clearEditingState() {
        _editingState.value = null
    }

    fun clearError() {
        _error.value = null
    }
}