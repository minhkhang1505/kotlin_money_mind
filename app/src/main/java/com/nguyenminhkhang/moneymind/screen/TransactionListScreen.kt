package com.nguyenminhkhang.moneymind.screen

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.SnackbarResult
import androidx.compose.material3.SwipeToDismissBox
import androidx.compose.material3.SwipeToDismissBoxValue
import androidx.compose.material3.Text
import androidx.compose.material3.rememberSwipeToDismissBoxState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar
import com.nguyenminhkhang.moneymind.data.local.model.Transaction
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel
import kotlinx.coroutines.launch

data class Task(
    val id: Int,
    val time: String,
    val title: String,
    val description: String,
    val isDone: Boolean = false
)

@Composable
fun TaskListScreen(
    dateList: List<String> = listOf("14 Mon", "15 Tue", "16 Wed", "17 Thu"),
    selectedDate: String = "14 Mon",
    navController: NavController,
    transactionViewModel: TransactionViewModel
) {
    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }
    Scaffold (
        topBar = { MyTopAppBar(showBackButton = false, title = "Today", onBackButtonClick = {}) },
        bottomBar = { MyBottomAppBar(navController) },
        snackbarHost = {
            SnackbarHost(hostState = snackbarHostState)
        },
    ) {
        Log.d("mk040515", transactionViewModel.transactions.toString())
        val scrollState = rememberScrollState()
        val transactions by transactionViewModel.transactions.collectAsState()
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .navigationBarsPadding()
                .padding(it)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(MaterialTheme.colorScheme.background)
                    .padding(16.dp)
            ) {
                // Date Row
                LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp),
                    modifier = Modifier.verticalScroll(scrollState)) {
                    items(dateList) { date ->
                        val isSelected = date == selectedDate
                        Box(
                            modifier = Modifier
                                .clip(RoundedCornerShape(10.dp))
                                .background(if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onPrimary)
                                .padding(vertical = 8.dp, horizontal = 16.dp)
                        ) {
                            Text(
                                text = date,
                                color = if (isSelected) Color.White else Color.Black
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                // Task List
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(12.dp),
                    modifier = Modifier.fillMaxHeight()
                ) {
                    items(transactions, key = { it.id }) { transaction ->
                        TaskItem(
                            transaction = transaction,
                            onDeleteTransaction = {deletedTransaction ->
                                transactionViewModel.deleteTransaction(deletedTransaction)

                                scope.launch {
                                    val result = snackbarHostState.showSnackbar(
                                        message= "Transaction deleted",
                                        actionLabel = "Undo"
                                    )

                                    if (result == SnackbarResult.ActionPerformed) {
                                        transactionViewModel.addTransaction(deletedTransaction)
                                    }
                                }
                            }
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(
    transaction: Transaction,
    onDeleteTransaction: (Transaction) -> Unit
) {
    val dismissState = rememberSwipeToDismissBoxState(
        confirmValueChange = {
            dismissValue ->
            if(dismissValue == SwipeToDismissBoxValue.EndToStart) {
                onDeleteTransaction(transaction)
                true
            } else {
                false
            }
        }
    )

    SwipeToDismissBox(
        state = dismissState,
        modifier = Modifier.fillMaxWidth()
            .padding(8.dp),
        enableDismissFromEndToStart = true,
        enableDismissFromStartToEnd = false,
        gesturesEnabled = true, // kich hoat thao tac keo
        backgroundContent =  {
            Box(
                modifier = Modifier.fillMaxSize()
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Red)
                    .padding(horizontal = 20.dp),
                contentAlignment = Alignment.CenterEnd
            ) {
                Icon(
                    imageVector = Icons.Default.Delete,
                    contentDescription = "Delete",
                    tint = Color.White
                )
            }
        },
        content = {
            Card(
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Row(
                    modifier = Modifier
                        .padding(16.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = transaction.time,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier.width(80.dp)
                    )
                    Column {
                        Text(transaction.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                        transaction.category?.let {
                            Text(text = it, fontSize = 14.sp, color = Color.Gray)
                        }
                    }
                }
            }
        }
    )
}