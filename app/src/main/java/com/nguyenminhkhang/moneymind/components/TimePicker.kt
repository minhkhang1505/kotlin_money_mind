package com.nguyenminhkhang.moneymind.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import java.util.Calendar

@Composable
fun TimePickerSample(
    selectedTime: String,
    onTimeSelected: (String) -> Unit
) {
    val currentTime = Calendar.getInstance()
    val selectedHour = remember { mutableIntStateOf(currentTime.get(Calendar.HOUR_OF_DAY)) }
    val selectedMinute = remember { mutableIntStateOf(currentTime.get(Calendar.MINUTE)) }
    val showDialog = remember { mutableStateOf(false) }

    val formattedTime = String.format("%02d:%02d", selectedHour.value, selectedMinute.value)

    Column (
    ) {
        OutlinedButton (
            onClick = { showDialog.value = true },
            shape = RoundedCornerShape(12.dp),
            modifier  = Modifier
                .fillMaxWidth()
                .height(56.dp),
        ) {
            Text("Selected Time: $formattedTime")
        }

        if (showDialog.value) {
            TimePickerDialogCustom(
                initialHour = selectedHour.value,
                initialMinute = selectedMinute.value,
                onDismiss = { showDialog.value = false },
                onConfirm = { hour, minute ->
                    selectedHour.value = hour
                    selectedMinute.value = minute
                    val selectedTime = String.format("%02d:%02d", hour, minute)
                    onTimeSelected(selectedTime)
                    showDialog.value = false
                }
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TimePickerDialogCustom(
    initialHour: Int,
    initialMinute: Int,
    onDismiss: () -> Unit,
    onConfirm: (hour: Int, minute: Int) -> Unit
) {
    val timePickerState = rememberTimePickerState(
        initialHour = initialHour,
        initialMinute = initialMinute,
        is24Hour = true,
    )

    AlertDialog(
        onDismissRequest = onDismiss,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm(timePickerState.hour, timePickerState.minute)
                }
            ) {
                Text("Xác nhận")
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) {
                Text("Hủy")
            }
        },
        title = { Text("Chọn giờ") },
        text = {
            TimePicker(state = timePickerState)
        }
    )
}


