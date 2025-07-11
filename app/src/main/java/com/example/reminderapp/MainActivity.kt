package com.example.reminderapp

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
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
import androidx.compose.material3.Scaffold
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
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.reminderapp.presentation.screens.main.components.AddReminderFAB
import com.example.reminderapp.presentation.screens.main.components.TimePickerDialog
import com.example.reminderapp.ui.theme.ReminderAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReminderAppTheme {
                val testList = List(15) {
                    ReminderItem("00:20", if (it < 5) null else "#$it")
                }
                App(testList)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ReminderItemRow(time: String, title: String?, modifier: Modifier = Modifier) {
    var isAlarmEnabled by remember { mutableStateOf(true) }
    val context = LocalContext.current

    var showDialog by remember { mutableStateOf(false) }
    var selectedTimeText by rememberSaveable { mutableStateOf(time) }

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
                    modifier = Modifier
                        .clickable {
                            showDialog = true
                        },
                    text = selectedTimeText,
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
                    OneTimeOrDailyButton({
                        Toast.makeText(context, "One Time Clicked", Toast.LENGTH_SHORT).show()
                    }, {
                        Toast.makeText(context, "Daily Clicked", Toast.LENGTH_SHORT).show()
                    })
                }
                Switch(
                    checked = isAlarmEnabled,
                    onCheckedChange = { isAlarmEnabled = it }
                )
            }
        }
    }

    if (showDialog) {
        TimePickerDialog(
            onDismissRequest = { showDialog = false },
            onConfirmRequest = { timePickerState ->
                val hour = timePickerState.hour.toString().padStart(2, '0')
                val minute = timePickerState.minute.toString().padStart(2, '0')
                selectedTimeText = "$hour:$minute"
                showDialog = false
            },
            initialHour = selectedTimeText.substring(0, 2).toInt(),
            initialMinute = selectedTimeText.substring(3, 5).toInt()
        )
    }
}

@Composable
fun OneTimeOrDailyButton(
    onOneTime: () -> Unit,
    onDaily: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedIndex by remember { mutableIntStateOf(0) }
    val options = listOf("OneTime", "Daily")

    SingleChoiceSegmentedButtonRow(
        modifier = modifier
    ) {
        options.forEachIndexed { index, label ->
            SegmentedButton(
                shape = SegmentedButtonDefaults.itemShape(
                    index = index,
                    count = options.size
                ),
                onClick = {
                    selectedIndex = index
                    if (selectedIndex == 0) {
                        onOneTime()
                    } else {
                        onDaily()
                    }
                },
                selected = index == selectedIndex,
                label = { Text(label) }
            )
        }
    }
}

@Composable
fun App(reminderItemRows: List<ReminderItem>) {
    Scaffold(
        floatingActionButton = { AddReminderFAB { } },
    ) { innerPadding ->
    }
}