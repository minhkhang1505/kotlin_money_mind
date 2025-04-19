package com.nguyenminhkhang.moneymind.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.AssistChip
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.components.DatePickerFieldToModal
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar
import com.nguyenminhkhang.moneymind.components.TimePickerSample
import com.nguyenminhkhang.moneymind.data.model.Transaction
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel

@Composable
fun AddTransactionScreen(navController: NavController) {
    val trasactionViewModel : TransactionViewModel = viewModel()
    val shortcuts = listOf(
       "Them", "Trà sữa", "Cơm trưa", "Bún bò", "Di chuyển", "Ăn sáng", "Ăn trưa", "Ăn tối"
    )
    val scrollState = rememberScrollState()
    var dateOfTransaction by remember { mutableStateOf<Long?>(null) }
    var timeOfTransaction by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(0.0) }
    var selectedCategory by remember { mutableStateOf("") }
    var note by remember { mutableStateOf<String>("") }

    Scaffold (
        topBar = {
            MyTopAppBar(title = "Add Transaction", showBackButton = false, onBackButtonClick = {})
        },
        bottomBar = {
            MyBottomAppBar(navController)
        },
        modifier = Modifier.fillMaxSize()
            .padding()
    ) {
        Column(
            modifier = Modifier.padding(8.dp)
                .padding(it)
                .verticalScroll(scrollState)
                .navigationBarsPadding()
        ) {
            DatePickerFieldToModal(
                selectedDate = dateOfTransaction,
                onDateSelected = { selectedDate ->
                    dateOfTransaction = selectedDate
                }
            )
            Spacer(modifier = Modifier.height(8.dp))
            TimePickerSample(
                selectedTime = timeOfTransaction,
                onTimeSelected = { selectedTime ->
                    timeOfTransaction = selectedTime
                }
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Quick Shortcuts")

            LazyRow(
                modifier = Modifier.padding(top = 8.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                items(shortcuts) { label ->
                    AssistChip(
                        onClick = {  },
                        label = { Text(label) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        },
                            modifier = Modifier.size(100.dp)
                    )
                }
            }
            OutlinedTextField(
                label = { Text("Title") },
                value = title,
                onValueChange = {title = it},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                label = { Text("Amount") },
                value = amountInput,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    amountInput = it
                    amount = it.toDoubleOrNull() ?: 0.0
                },
                modifier = Modifier.fillMaxWidth()
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text ("Categories")

            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                AssistChip(
                    onClick = {  },
                    label = { Text("Dau tu") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    },
                )
                AssistChip(
                    onClick = {  },
                    label = { Text("Dau tu") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    },
                )
                AssistChip(
                    onClick = {  },
                    label = { Text("Dau tu") },
                    leadingIcon = {
                        Icon(imageVector = Icons.Default.Add, contentDescription = null)
                    },
                )
            }
            OutlinedTextField(
                label = { Text("Category") },
                value = selectedCategory,
                onValueChange = {selectedCategory = it},
                modifier = Modifier.fillMaxWidth()
            )
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                placeholder = { Text("Enter your note (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                singleLine = false,
                maxLines = 5
            )
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val newTransaction = Transaction.create(
                        title = title,
                        date = dateOfTransaction,
                        time = timeOfTransaction,
                        amount = amount,
                        category = selectedCategory,
                        description = note
                    )
                    trasactionViewModel.addTransaction(newTransaction) }

            ) {
                Log.d("AddTransactionScreen", "Transaction added: $newTransaction")
                Text(text = "Save")
            }
        }
    }
}