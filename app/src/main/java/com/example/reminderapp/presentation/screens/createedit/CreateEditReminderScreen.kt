package com.example.reminderapp.presentation.screens.createedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.presentation.screens.createedit.components.TitleTextField
import com.example.reminderapp.presentation.screens.main.ReminderViewModel
import com.example.reminderapp.presentation.screens.main.models.ReminderUiState
import com.example.reminderapp.ui.components.timepicker.TimePicker
import com.example.reminderapp.utils.ReminderUtils

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditReminderScreen(
    reminderViewModel: ReminderViewModel,
    reminderId: String?,
    onNavigateBack: () -> Unit
) {
    val existingReminder = reminderId?.let { id ->
        reminderViewModel.reminders.collectAsState().value.find { it.id == id }
    }

    var title by rememberSaveable {
        mutableStateOf(existingReminder?.title ?: "")
    }

    var reminderType by rememberSaveable {
        mutableStateOf(existingReminder?.reminderType ?: ReminderType.ONE_TIME)
    }

    val initialTime = existingReminder?.time?.let {
        ReminderUtils.stringToTime(it)
    } ?: ReminderUtils.getCurrentTime()
    val initialHour = initialTime.hour
    val initialMinute = initialTime.minute

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(if (existingReminder != null) "Edit Reminder" else "New Reminder")
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .padding(paddingValues)
                .padding(16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            TitleTextField(
                value = TextFieldValue(title),
                onValueChange = { newValue -> title = newValue.text }
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                var selectedIndex by remember(reminderType) {
                    mutableIntStateOf(if (reminderType == ReminderType.ONE_TIME) 0 else 1)
                }
                val options = listOf("One Time", "Daily")

                SingleChoiceSegmentedButtonRow {
                    options.forEachIndexed { index, label ->
                        SegmentedButton(
                            shape = SegmentedButtonDefaults.itemShape(
                                index = index,
                                count = options.size
                            ),
                            onClick = {
                                selectedIndex = index
                                reminderType = if (index == 0) ReminderType.ONE_TIME else ReminderType.DAILY
                            },
                            selected = index == selectedIndex,
                            label = { Text(label) }
                        )
                    }
                }
            }

            TimePicker(
                onConfirm = { timePickerState ->
                    val time = ReminderUtils.timeToString(
                        Time(
                            timePickerState.hour,
                            timePickerState.minute
                        )
                    )

                    if (existingReminder != null) {
                        val updatedReminder = existingReminder.copy(
                            title = title.ifBlank { null },
                            time = time,
                            reminderType = reminderType
                        )
                        reminderViewModel.updateReminder(updatedReminder)
                    } else {
                        reminderViewModel.createReminder(
                            ReminderUiState(
                                title = title.ifBlank { null },
                                time = time,
                                isEnabled = true,
                                reminderType = reminderType
                            )
                        )
                    }
                    onNavigateBack()
                },
                onDismiss = onNavigateBack,
                initialHour = initialHour,
                initialMinute = initialMinute
            )
        }
    }
}