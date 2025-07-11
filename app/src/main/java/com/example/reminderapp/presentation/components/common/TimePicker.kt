package com.example.reminderapp.presentation.components.common

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerState
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePicker(
    onConfirm: (TimePickerState) -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    initialHour: Int? = null,
    initialMinute: Int? = null
) {
    val (hour, minute) = if (initialHour != null && initialMinute != null) {
        initialHour to initialMinute
    } else {
        val now = Calendar.getInstance()
        now.get(Calendar.HOUR_OF_DAY) to now.get(Calendar.MINUTE)
    }

    val timePickerState = rememberTimePickerState(
        initialHour = hour,
        initialMinute = minute,
        is24Hour = true,
    )

    Box(
        modifier = modifier
            .wrapContentSize()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            modifier = Modifier
                .wrapContentWidth()
                .align(Alignment.Center),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TimePicker(
                state = timePickerState,
                modifier = Modifier.align(Alignment.CenterHorizontally)
            )
            Row(
                modifier = Modifier
                    .align(Alignment.End)
                    .padding(top = 8.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                TextButton(onClick = onDismiss) {
                    Text("Cancel")
                }
                Button(onClick = { onConfirm(timePickerState) }) {
                    Text("Confirm")
                }
            }
        }
    }
}