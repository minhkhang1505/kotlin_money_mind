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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.nguyenminhkhang.moneymind.components.MyBottomAppBar
import com.nguyenminhkhang.moneymind.components.MyTopAppBar
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel

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
    tasks: List<Task>,
    navController: NavController
) {
    Scaffold (
        topBar = { MyTopAppBar(showBackButton = false, title = "Today", onBackButtonClick = {}) },
        bottomBar = { MyBottomAppBar(navController) }
    ) {
        val transactionViewModel: TransactionViewModel = viewModel()
        Log.d("mk040515", transactionViewModel.transactions.toString())
        val scrollState = rememberScrollState()
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
                    items(tasks) { task ->
                        TaskItem(task)
                    }
                }
            }
        }
    }
}

@Composable
fun TaskItem(task: Task) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(12.dp))
            .background(if (task.isDone) Color(0xFFEDEDED) else Color.White)
            .padding(16.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                text = task.time,
                fontWeight = FontWeight.Bold,
                fontSize = 14.sp,
                modifier = Modifier.width(80.dp)
            )
            Column {
                Text(task.title, fontWeight = FontWeight.Bold, fontSize = 16.sp)
                task.description?.let {
                    Text(text = it, fontSize = 14.sp, color = Color.Gray)
                }
            }
        }
    }
}


//@Preview(showBackground = true)
//@Composable
//fun PreviewTaskListScreen() {
//    val dummyTasks = listOf(
//        Task(1, "6:00 - 7:30", "Fitness", "Exercise and gym"),
//        Task(2, "7:30 - 8:00", "Check Emails and sms", "Review and respond to emails and SMS"),
//        Task(3, "8:00 - 10:00", "Work on Projects", "Focus on the tasks related to Project"),
//        Task(4, "10:00 - 11:00", "Attend Meeting", "Team meeting with client ABC"),
//        Task(5, "11:00 - 13:00", "Work of XYZ", "Change theme and ideas in XYZ")
//    )
//    TaskListScreen(tasks = dummyTasks, onAddNewClick = {}, navController = NavController)
//}
