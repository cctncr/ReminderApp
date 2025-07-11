package com.example.reminderapp.presentation.screens.main

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.reminderapp.presentation.screens.main.components.AddReminderFAB
import com.example.reminderapp.presentation.screens.main.components.ReminderItemRow
import androidx.compose.foundation.lazy.items

@Composable
fun MainScreen(
    viewModel: ReminderViewModel,
    onNavigateToCreateReminder: () -> Unit,
    onNavigateToEditReminder: (String) -> Unit
) {
    val reminders by viewModel.reminders.collectAsState()

    val sortedReminders = reminders.sortedBy { reminder ->
        val timeParts = reminder.time.split(":")
        val hour = timeParts.getOrNull(0)?.toIntOrNull() ?: 0
        val minute = timeParts.getOrNull(1)?.toIntOrNull() ?: 0
        hour * 60 + minute
    }

    Scaffold(
        floatingActionButton = {
            AddReminderFAB(
                onClick = onNavigateToCreateReminder
            )
        }
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(
                items = sortedReminders,
                key = { it.id }
            ) { reminder ->
                ReminderItemRow(
                    time = reminder.time,
                    title = reminder.title,
                    isAlarmEnabled = reminder.isEnabled,
                    reminderType = reminder.reminderType,
                    onAlarmToggle = { enabled ->
                        viewModel.toggleEnabled(reminder.id, enabled)
                    },
                    onTimeChange = { newTime ->
                        viewModel.updateTime(reminder.id, newTime)
                    },
                    onReminderTypeChange = { reminderType ->
                        viewModel.updateType(reminder.id, reminderType)
                    },
                    onReminderClick = {
                        onNavigateToEditReminder(reminder.id)
                    }
                )
            }
        }
    }
}