package com.nguyenminhkhang.moneymind

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.nguyenminhkhang.moneymind.data.local.AppDatabase
import com.nguyenminhkhang.moneymind.data.local.CurrencyViewModelFactory
import com.nguyenminhkhang.moneymind.data.local.TransactionViewModelFactory
import com.nguyenminhkhang.moneymind.data.repository.ConverterHistoryRepository
import com.nguyenminhkhang.moneymind.data.repository.TransactionRepository
import com.nguyenminhkhang.moneymind.screen.AccountScreen
import com.nguyenminhkhang.moneymind.screen.AddTransactionScreen
import com.nguyenminhkhang.moneymind.screen.CurrencyConverterScreen
import com.nguyenminhkhang.moneymind.screen.DashboardScreen
import com.nguyenminhkhang.moneymind.screen.Task
import com.nguyenminhkhang.moneymind.screen.TaskListScreen
import com.nguyenminhkhang.moneymind.ui.theme.MoneyMindTheme
import com.nguyenminhkhang.moneymind.viewmodel.CurrencyViewModel
import com.nguyenminhkhang.moneymind.viewmodel.DashboardViewModel
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val navController = rememberNavController()
            MoneyMindTheme {
                MyApp(navController)
            }
        }
    }
}

@Composable
fun MyApp (navController: NavHostController) {
    val dummyTasks = listOf(
        Task(1, "6:00 - 7:30", "Fitness", "Exercise and gym"),
        Task(2, "7:30 - 8:00", "Check Emails and sms", "Review and respond to emails and SMS"),
        Task(3, "8:00 - 10:00", "Work on Projects", "Focus on the tasks related to Project"),
        Task(4, "10:00 - 11:00", "Attend Meeting", "Team meeting with client ABC"),
        Task(5, "11:00 - 13:00", "Work of XYZ", "Change theme and ideas in XYZ"),
        Task(6, "8:00 - 10:00", "Work on Projects", "Focus on the tasks related to Project"),
        Task(7, "10:00 - 11:00", "Attend Meeting", "Team meeting with client ABC"),
        Task(8, "11:00 - 13:00", "Work of XYZ", "Change theme and ideas in XYZ"),
    )

    val context = LocalContext.current
    val database = AppDatabase.getDatabase(context)

    //Transactions
    val repository = TransactionRepository(database.transactionDao())
    val transactionViewModelFactory = TransactionViewModelFactory(repository)
    val transactionViewModel: TransactionViewModel = viewModel(factory = transactionViewModelFactory)

    //Cerrency Converter
    val converterRepository = ConverterHistoryRepository(database.converterhistoryDao())
    val converterViewModelFactory = CurrencyViewModelFactory(converterRepository)
    val currencyViewModel: CurrencyViewModel = viewModel(factory = converterViewModelFactory)

    val dashboardViewModel : DashboardViewModel = viewModel()

    NavHost(navController = navController, startDestination = "dashboard_screen") {

        composable(route = Screen.TaskListScreen.route) {
            TaskListScreen(navController = navController, transactionViewModel = transactionViewModel )
        }

        composable(route = Screen.AddTransactionScreen.route) {
            AddTransactionScreen(navController = navController, transactionViewModel = transactionViewModel)
        }

        composable(route = Screen.CurrencyConverterScreen.route) {
            CurrencyConverterScreen(navController = navController, currencyViewModel = currencyViewModel)
        }

        composable(route = Screen.AccountScreen.route) {
            AccountScreen(navController = navController)
        }

        composable(route = Screen.DashboardScreen.route) {
            DashboardScreen(navController = navController, dashboardViewModel = dashboardViewModel)
        }
    }
}
