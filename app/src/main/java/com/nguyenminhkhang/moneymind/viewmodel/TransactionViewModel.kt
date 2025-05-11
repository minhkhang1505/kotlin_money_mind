package com.nguyenminhkhang.moneymind.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyenminhkhang.moneymind.data.local.model.Transaction
import com.nguyenminhkhang.moneymind.data.local.model.TransactionCategory
import com.nguyenminhkhang.moneymind.data.repository.TransactionCategoryRepository
import com.nguyenminhkhang.moneymind.data.repository.TransactionRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class TransactionViewModel(private val repository: TransactionRepository, private val categoryRepository: TransactionCategoryRepository) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions.asStateFlow()

    private val _categories = MutableStateFlow<List<TransactionCategory>>(emptyList())
    val categories: StateFlow<List<TransactionCategory>> = _categories.asStateFlow()


    var transactionShortcut by mutableStateOf(listOf<Transaction>())

    init {
        viewModelScope.launch {
            repository.getAllTransactions().collectLatest { transactions ->
                _transactions.value = transactions
            }
        }
    }

    init {
        viewModelScope.launch {
            categoryRepository.getAllCategories().collectLatest { categories ->
                _categories.value = categories
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.insertTransaction(transaction)

        }   }

    fun addCategory(category: TransactionCategory) {
        viewModelScope.launch {
            categoryRepository.insertCategory(category)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            repository.deleteTransaction(transaction)
        }
    }
}