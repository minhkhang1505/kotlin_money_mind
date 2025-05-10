package com.nguyenminhkhang.moneymind.data.local

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.nguyenminhkhang.moneymind.data.repository.ConverterHistoryRepository
import com.nguyenminhkhang.moneymind.viewmodel.CurrencyViewModel

class CurrencyViewModelFactory(
    private val repository: ConverterHistoryRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CurrencyViewModel::class.java)) {
            return CurrencyViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
