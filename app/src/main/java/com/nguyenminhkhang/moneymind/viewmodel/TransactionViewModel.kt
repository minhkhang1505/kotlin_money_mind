package com.nguyenminhkhang.moneymind.viewmodel

import android.view.SurfaceControl
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nguyenminhkhang.moneymind.data.model.Transaction
import com.nguyenminhkhang.moneymind.data.model.TransactionCategory
import com.nguyenminhkhang.moneymind.data.model.defaultCategories
import java.util.UUID

class TransactionViewModel : ViewModel() {
    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    private var categories by mutableStateOf(defaultCategories)

    var transactionShortcut by mutableStateOf(listOf(
        Transaction(
            title = "Bún bò",
            amount = 30000.0,
            date = System.currentTimeMillis(), // Bạn có thể thay bằng thời gian thực tế
            time = "12:30 PM",
            category = "Eat and drink",
            description = "Món bún bò Huế đặc trưng",
            id = UUID.randomUUID().toString()
        ),
        Transaction(
            title = "Cà phê",
            amount = 25000.0,
            date = System.currentTimeMillis(),
            time = "9:00 AM",
            category = "Drink",
            description = "Cà phê sáng",
            id = UUID.randomUUID().toString()
        ),
        Transaction(
            title = "Xe buýt",
            amount = 7000.0,
            date = System.currentTimeMillis(),
            time = "8:00 AM",
            category = "Transport",
            description = "Đi lại bằng xe buýt",
            id = UUID.randomUUID().toString()
        ),
        Transaction(
            title = "Mua sắm",
            amount = 150000.0,
            date = System.currentTimeMillis(),
            time = "3:00 PM",
            category = "Shopping",
            description = "Mua quần áo",
            id = UUID.randomUUID().toString()
        )
    ))


    fun addTransaction(transaction: Transaction) {
        transactions = transactions.toMutableList().apply { add(transaction) }
    }

    fun addCategory(category: TransactionCategory) {
        categories = categories.toMutableList().apply { add(category) }
    }

    fun getCategoryList(): List<TransactionCategory> = categories

}