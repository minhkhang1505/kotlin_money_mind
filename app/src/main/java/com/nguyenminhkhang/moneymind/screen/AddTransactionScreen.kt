package com.nguyenminhkhang.moneymind.screen

import androidx.annotation.OptIn
import androidx.compose.foundation.clickable
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.KeyboardArrowDown
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material3.AssistChip
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.semantics.Role.Companion.Switch
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.dp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.components.AlertDialogExample
import com.nguyenminhkhang.moneymind.components.DatePickerFieldToModal
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar
import com.nguyenminhkhang.moneymind.components.TimePickerSample
import com.nguyenminhkhang.moneymind.data.local.model.Transaction
import com.nguyenminhkhang.moneymind.data.local.model.TransactionCategory
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel

@OptIn(UnstableApi::class)
@Composable
fun AddTransactionScreen(
    navController: NavController,
    transactionViewModel: TransactionViewModel,
) {
    val shortcuts = transactionViewModel.transactionShortcut
    val scrollState = rememberScrollState()
    var dateOfTransaction by remember { mutableStateOf<Long?>(null) }
    var timeOfTransaction by remember { mutableStateOf("") }
    var title by remember { mutableStateOf("") }
    var amountInput by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf(0.0) }
    var selectedCategory by remember { mutableStateOf("") }
    var note by remember { mutableStateOf("") }
    var isExpandedCategory by remember { mutableStateOf(false) }
    val categories by transactionViewModel.categories.collectAsState()
    var typeOfTransaction by remember { mutableStateOf("Expense") }
    var mTextFieldSize by remember { mutableStateOf(IntSize.Zero) }
    var showNoticeSaved by remember { mutableStateOf(false) }

    var isChecked by remember { mutableStateOf(false) }

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
        if (showNoticeSaved) {
            AlertDialogExample(
                dialogTitle = "Transaction saved",
                onConfirmation = { showNoticeSaved = false },
                dialogText = "Your transaction has been successfully saved. You can now view it in your transaction screen.",
                onDismissRequest = { showNoticeSaved = false },
                icon = Icons.Default.Notifications
            )
        }
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
                items(shortcuts) { shortcut ->
                    AssistChip(
                        onClick = {
                            title = shortcut.title
                            amountInput = shortcut.amount.toString()
                            selectedCategory = shortcut.category
                            note = shortcut.description.toString()
                        },
                        label = { Text(shortcut.title) },
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
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )
            OutlinedTextField(
                label = { Text("Amount") },
                value = amountInput,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                onValueChange = {
                    amountInput = it
                    amount = it.toDoubleOrNull() ?: 0.0
                },
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))
            Text ("Quick select category")

            Row (
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState())
            ) {
                categories.forEach { category ->
                    AssistChip(
                        onClick = { selectedCategory = category.name },
                        label = { Text(category.name) },
                        leadingIcon = {
                            Icon(imageVector = Icons.Default.Add, contentDescription = null)
                        },
                    )
                }
            }

            //change to dropdown menu categories
            OutlinedTextField(
                label = { Text("Category") },
                value = selectedCategory,
                onValueChange = {selectedCategory = it},
                modifier = Modifier.fillMaxWidth()
                    .onGloballyPositioned { coordinates ->
                        mTextFieldSize = coordinates.size
                    },
                shape = RoundedCornerShape(12.dp),
                trailingIcon = {
                    Icon(
                        imageVector = Icons.Default.KeyboardArrowDown,
                        contentDescription = null,
                        Modifier.clickable {  isExpandedCategory = !isExpandedCategory}
                    )
                }
            )
            DropdownMenu(
                expanded = isExpandedCategory,
                onDismissRequest = { isExpandedCategory = false },
                modifier = Modifier
                    .width(with(LocalDensity.current){mTextFieldSize.width.toDp()})

            ) {
                categories.forEach { category ->
                    DropdownMenuItem(
                        onClick = {
                            selectedCategory = category.name
                            isExpandedCategory = false
                        },
                        text = { Text(text = category.name) }
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))
            Text(text = "Type of Transaction")
            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                modifier = Modifier.horizontalScroll(rememberScrollState()).fillMaxWidth()
            ) {
                RadioButton(
                    selected = typeOfTransaction == "Expense",
                    onClick = { typeOfTransaction = "Expense" }
                )
                Text(
                    text = "Expense", modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically)
                )
                RadioButton(
                    selected = typeOfTransaction == "Income",
                    onClick = { typeOfTransaction = "Income" }
                )
                Text(
                    text = "Income", modifier = Modifier.fillMaxHeight().align(Alignment.CenterVertically)
                )
            }
            OutlinedTextField(
                value = note,
                onValueChange = { note = it },
                label = { Text("Note") },
                placeholder = { Text("Enter your note (optional)") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                shape = RoundedCornerShape(12.dp),
                singleLine = false,
                maxLines = 5
            )
            Spacer(modifier = Modifier.height(8.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(text = "Save this transaction")

                Switch(
                    checked = isChecked,
                    onCheckedChange = { checked ->
                        isChecked = checked
                    }
                )
            }
            Spacer(modifier = Modifier.height(8.dp))
            ElevatedButton(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    val newTransaction = Transaction(
                        title = title,
                        date = dateOfTransaction,
                        time = timeOfTransaction,
                        amount = amount,
                        category = selectedCategory,
                        description = note
                    )
                    transactionViewModel.addTransaction(newTransaction)
                    if (isChecked) {
                        transactionViewModel.addCategory(
                            category = TransactionCategory(
                                name = selectedCategory,
                                type = typeOfTransaction
                            )
                        )
                    }
                    showNoticeSaved = true
                },
                shape = RoundedCornerShape(12.dp)
            ) {
                Text(text = "Save")
            }
        }
    }
}