package com.example.reminderapp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reminderapp.presentation.screens.main.components.ReminderItemRow
import com.example.reminderapp.presentation.viewmodel.ReminderViewModel

@Composable
fun ReminderScreen(
    viewModel: ReminderViewModel
) {
    val reminders by viewModel.reminders.collectAsState()

    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.spacedBy(8.dp),
        contentPadding = PaddingValues(8.dp)
    ) {
        items(reminders.size) { index ->
            val reminder = reminders[index]
            ReminderItemRow(
                time = reminder.time,
                title = reminder.title,
                isAlarmEnabled = reminder.isEnabled,
                onAlarmToggle = { enabled ->
                    viewModel.toggleEnabled(reminder.id, enabled)
                },
                onTimeChange = { newTime ->
                    viewModel.updateTime(reminder.id, newTime)
                },
                onReminderTypeChange = { reminderType ->
                    viewModel.updateType(reminder.id, reminderType)
                },
                reminderType = reminder.reminderType,
                onReminderClick = {},
            )
        }
    }
}