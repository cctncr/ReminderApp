package com.example.reminderapp.presentation.screens.createedit

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import com.example.reminderapp.OneTimeOrDailyButton
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.presentation.screens.createedit.components.TitleTextField
import com.example.reminderapp.presentation.screens.main.ReminderViewModel
import com.example.reminderapp.presentation.screens.main.models.ReminderUiState
import com.example.reminderapp.ui.components.timepicker.TimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditReminderScreen(
    reminderViewModel: ReminderViewModel
) {
    var title by rememberSaveable { mutableStateOf("") }
    var isOneTime by rememberSaveable { mutableStateOf(true) }
    val reminderType = if (isOneTime) ReminderType.ONE_TIME else ReminderType.DAILY

    Scaffold(
        topBar = {
            TitleTextField(
                value = TextFieldValue(title),
                onValueChange = { newValue -> title = newValue.text },
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier.padding(paddingValues)
        ) {
            OneTimeOrDailyButton(
                onOneTime = { isOneTime = true },
                onDaily = { isOneTime = false }
            )
            TimePicker(
                onConfirm = { timePickerState ->
                    val time = "${timePickerState.hour}:${timePickerState.minute}"
                    reminderViewModel.createReminder(
                        ReminderUiState(
                            title = title,
                            time = time,
                            isEnabled = true,
                            reminderType = reminderType
                        )
                    )
                },
                onDismiss = { },
            )
        }
    }
}

@Composable
@Preview
fun CreateEditReminderScreen_Preview() {
    CreateEditReminderScreen(reminderViewModel = ReminderViewModel())
}