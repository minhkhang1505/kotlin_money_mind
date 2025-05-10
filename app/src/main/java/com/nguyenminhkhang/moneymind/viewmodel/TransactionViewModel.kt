package com.nguyenminhkhang.moneymind.viewmodel

import android.app.Application
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyenminhkhang.moneymind.data.local.AppDatabase
import com.nguyenminhkhang.moneymind.data.local.Transaction
import com.nguyenminhkhang.moneymind.data.local.dao.TransactionDAO
import com.nguyenminhkhang.moneymind.data.model.TransactionCategory
import com.nguyenminhkhang.moneymind.data.model.defaultCategories
import com.nguyenminhkhang.moneymind.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private var categories by mutableStateOf(defaultCategories)

    var transactionShortcut by mutableStateOf(listOf<Transaction>())

    init {
        viewModelScope.launch {
            repository.getAllTransactions().collectLatest { transactions ->
                _transactions.value = transactions
            }
        }
    }


    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)

        }   }

    fun addCategory(category: TransactionCategory) {
        categories = categories.toMutableList().apply { add(category) }
    }

    fun getCategoryList(): List<TransactionCategory> = categories

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }
}