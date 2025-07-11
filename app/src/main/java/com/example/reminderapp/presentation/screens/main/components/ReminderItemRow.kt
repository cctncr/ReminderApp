package com.example.reminderapp.presentation.screens.main.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SegmentedButton
import androidx.compose.material3.SegmentedButtonDefaults
import androidx.compose.material3.SingleChoiceSegmentedButtonRow
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderapp.domain.models.ReminderType

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderItemRow(
    time: String,
    title: String?,
    isAlarmEnabled: Boolean,
    reminderType: ReminderType,
    onAlarmToggle: (Boolean) -> Unit,
    onTimeChange: (String) -> Unit,
    onReminderTypeChange: (ReminderType) -> Unit,
    onReminderClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var showDialog by remember { mutableStateOf(false) }

    Surface(
        modifier = modifier
            .padding(horizontal = 8.dp, vertical = 4.dp)
            .fillMaxWidth()
            .clickable { onReminderClick() },
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.secondary),
        shape = RoundedCornerShape(8.dp),
        shadowElevation = 4.dp
    ) {
        Column(
            Modifier.padding(horizontal = 12.dp, vertical = 8.dp)
        ) {
            if (!title.isNullOrEmpty()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleSmall,
                    color = if (isAlarmEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    }
                )
                Spacer(modifier = Modifier.height(4.dp))
            }
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    modifier = Modifier
                        .clickable(enabled = isAlarmEnabled) {
                            showDialog = true
                        }
                        .padding(vertical = 4.dp),
                    text = time,
                    style = MaterialTheme.typography.headlineLarge,
                    color = if (isAlarmEnabled) {
                        MaterialTheme.colorScheme.primary
                    } else {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.4f)
                    },
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    if (isAlarmEnabled) {
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
                                        val newType = if (index == 0) ReminderType.ONE_TIME else ReminderType.DAILY
                                        onReminderTypeChange(newType)
                                    },
                                    selected = index == selectedIndex,
                                    label = { Text(label, fontSize = 12.sp) }
                                )
                            }
                        }
                    }

                    Switch(
                        checked = isAlarmEnabled,
                        onCheckedChange = onAlarmToggle
                    )
                }
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