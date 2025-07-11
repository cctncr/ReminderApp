package com.example.reminderapp.presentation.screens.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.reminderapp.domain.models.Time
import com.example.reminderapp.presentation.components.common.TimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (Time) -> Unit,
    initialTime: Time? = null
) {
    val initialHour = initialTime?.hour
    val initialMinute = initialTime?.minute

    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            TimePicker(
                onConfirm = { state ->
                    onConfirmRequest(Time(state.hour, state.minute))
                },
                onDismiss = onDismissRequest,
                initialHour = initialHour,
                initialMinute = initialMinute
            )
        }
    }
}