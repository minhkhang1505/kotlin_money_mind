package com.nguyenminhkhang.moneymind

sealed class Screen(val route: String) {
    object TaskListScreen : Screen("task_list_screen")
    object AddTransactionScreen : Screen("add_transaction_screen")
    object CurrencyConverterScreen : Screen("currency_converter_screen")
    object AccountScreen : Screen("account_screen")
    object DashboardScreen : Screen("dashboard_screen")
}