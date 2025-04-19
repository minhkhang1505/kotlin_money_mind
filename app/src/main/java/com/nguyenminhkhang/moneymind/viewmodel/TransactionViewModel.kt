package com.nguyenminhkhang.moneymind.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import com.nguyenminhkhang.moneymind.data.model.Transaction

class TransactionViewModel : ViewModel() {
    val transactions = mutableStateListOf<Transaction>()

    fun addTransaction(transaction: Transaction) {
        transactions.add(transaction)
    }
}