package com.example.reminderapp.presentation.screens.createedit

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.example.reminderapp.domain.models.ReminderType
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.presentation.components.common.TimePicker
import com.example.reminderapp.presentation.models.EditingState
import com.example.reminderapp.presentation.screens.createedit.components.TitleTextField
import com.example.reminderapp.presentation.viewmodel.ReminderViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateEditReminderScreen(
    reminderViewModel: ReminderViewModel,
    reminderId: String?,
    onNavigateBack: () -> Unit
) {
    val editingState: EditingState? by reminderViewModel.editingState.collectAsState()
    val isLoading by reminderViewModel.isLoading.collectAsState()
    val error by reminderViewModel.error.collectAsState()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(reminderId) {
        if (reminderId != null) {
            reminderViewModel.startEditing(reminderId)
        } else {
            reminderViewModel.startCreating()
        }
    }

    DisposableEffect(Unit) {
        onDispose {
            reminderViewModel.clearEditingState()
            reminderViewModel.clearError()
        }
    }

    LaunchedEffect(error) {
        error?.let {
            snackbarHostState.showSnackbar(it)
            reminderViewModel.clearError()
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        if (editingState?.isEditMode == true) "Edit Reminder"
                        else "New Reminder"
                    )
                },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                    }
                }
            )
        },
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        editingState?.let { state ->
            Column(
                modifier = Modifier
                    .padding(paddingValues)
                    .padding(16.dp)
                    .fillMaxSize(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                TitleTextField(
                    value = TextFieldValue(state.reminder.title ?: ""),
                    onValueChange = { newValue ->
                        reminderViewModel.updateEditingTitle(newValue.text)
                    }
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    var selectedIndex by remember(state.reminder.reminderType) {
                        mutableIntStateOf(
                            if (state.reminder.reminderType == ReminderType.ONE_TIME) 0 else 1
                        )
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
                                    val newType = if (index == 0)
                                        ReminderType.ONE_TIME else ReminderType.DAILY
                                    reminderViewModel.updateEditingType(newType)
                                },
                                selected = index == selectedIndex,
                                label = { Text(label) }
                            )
                        }
                    }
                }

                val currentTime = state.reminder.time.split(":").let {
                    Time(
                        it.getOrNull(0)?.toIntOrNull() ?: 0,
                        it.getOrNull(1)?.toIntOrNull() ?: 0
                    )
                }

                TimePicker(
                    onConfirm = { timePickerState ->
                        reminderViewModel.updateEditingTime(
                            Time(timePickerState.hour, timePickerState.minute)
                        )
                        reminderViewModel.saveReminder()
                        onNavigateBack()
                    },
                    onDismiss = onNavigateBack,
                    initialHour = currentTime.hour,
                    initialMinute = currentTime.minute
                )

                if (isLoading) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        CircularProgressIndicator()
                    }
                }
            }
        } ?: run {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                CircularProgressIndicator()
            }
        }
    }
}