package com.example.reminderapp.presentation.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderapp.OneTimeOrDailyButton
import com.example.reminderapp.domain.models.ReminderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderItemRow(
    time: String,
    title: String?,
    isAlarmEnabled: Boolean,
    onAlarmToggle: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit,
    onReminderTypeChance: (ReminderType) -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .padding(8.dp),
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            Modifier.padding(horizontal = 8.dp, vertical = 4.dp)
        ) {
            if (title != null) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isAlarmEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    }
                )
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier.clickable { showDialog = true },
                    text = time,
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (isAlarmEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    },
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold
                )
                if (isAlarmEnabled) {
                    OneTimeOrDailyButton(
                        onOneTime = { onReminderTypeChance(ReminderType.ONE_TIME) },
                        onDaily = { onReminderTypeChance(ReminderType.DAILY) }
                    )
                }
                Switch(
                    checked = isAlarmEnabled,
                    onCheckedChange = onAlarmToggle
                )
            }
        }
    }

    if (showDialog) {
        TimePickerDialog(
            onDismissRequest = { showDialog = false },
            onConfirmRequest = { state ->
                val newTime = "${state.hour.toString().padStart(2, '0')}:${
                    state.minute.toString().padStart(2, '0')
                }"
                onTimeChange(newTime)
                showDialog = false
            },
            initialHour = time.substring(0, 2).toInt(),
            initialMinute = time.substring(3, 5).toInt()
        )
    }
}

@Composable
@Preview
fun ReminderItemRow_Preview() {
    ReminderItemRow(
        time = "12:43",
        "title",
        true,
        {},
        {},
        {},
    )
}