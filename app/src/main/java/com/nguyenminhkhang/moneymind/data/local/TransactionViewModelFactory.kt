package com.nguyenminhkhang.moneymind.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenminhkhang.moneymind.data.repository.TransactionCategoryRepository
import com.nguyenminhkhang.moneymind.data.repository.TransactionRepository
import com.nguyenminhkhang.moneymind.viewmodel.TransactionViewModel

class TransactionViewModelFactory(
    private val repository: TransactionRepository,
    private val transactionCategoryRepository: TransactionCategoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(TransactionViewModel::class.java)) {
            return TransactionViewModel(repository, transactionCategoryRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

