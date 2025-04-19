package com.nguyenminhkhang.moneymind.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.nguyenminhkhang.moneymind.data.model.Transaction
import com.nguyenminhkhang.moneymind.data.model.TransactionCategory
import com.nguyenminhkhang.moneymind.data.model.defaultCategories

class TransactionViewModel : ViewModel() {
    var transactions by mutableStateOf<List<Transaction>>(emptyList())
        private set

    private var categories by mutableStateOf(defaultCategories)

    fun addTransaction(transaction: Transaction) {
        transactions = transactions.toMutableList().apply { add(transaction) }
    }

    fun addCategory(category: TransactionCategory) {
        categories = categories.toMutableList().apply { add(category) }
    }

    fun getCategoryList(): List<TransactionCategory> = categories

}