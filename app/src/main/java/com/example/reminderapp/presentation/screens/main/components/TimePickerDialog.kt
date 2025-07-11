package com.example.reminderapp.presentation.screens.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.reminderapp.ui.components.timepicker.TimePicker

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialog(
    onDismissRequest: () -> Unit,
    onConfirmRequest: (TimePickerState) -> Unit,
    initialHour: Int? = null,
    initialMinute: Int? = null
) {
    Dialog(
        onDismissRequest = onDismissRequest
    ) {
        Card(
            modifier = Modifier
                .wrapContentSize()
                .padding(8.dp)
        ) {
            TimePicker(
                onConfirm = { onConfirmRequest(it) },
                onDismiss = onDismissRequest,
                initialHour = initialHour,
                initialMinute = initialMinute
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
@Preview
fun TimePickerDialog_Preview() {
    TimePickerDialog(
        onDismissRequest = {},
        onConfirmRequest = {},
    )
}