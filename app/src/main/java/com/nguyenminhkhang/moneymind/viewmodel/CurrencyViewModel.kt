package com.nguyenminhkhang.moneymind.viewmodel

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nguyenminhkhang.moneymind.data.local.model.ConverterHistory
import com.nguyenminhkhang.moneymind.data.model.RetrofitInstance
import com.nguyenminhkhang.moneymind.data.repository.ConverterHistoryRepository
import kotlinx.coroutines.launch

class CurrencyViewModel(private val converterHistoryRepository: ConverterHistoryRepository): ViewModel() {
    var rates by mutableStateOf<Map<String, Double>>(emptyMap())
    var currencies by mutableStateOf<List<String>>(emptyList())
    var isLoading by mutableStateOf(false)

    var converterHistoryList by mutableStateOf<List<ConverterHistory>>(emptyList())

    init {
        viewModelScope.launch {
            converterHistoryRepository.getAllConverterHistories().collect {
                converterHistoryList = it
            }
        }
    }

    fun fetchRates(apiKey: String, base: String) {
        viewModelScope.launch {
            try {
                isLoading = true
                val response = RetrofitInstance.api.getRates(apiKey, base)
                if (response.result == "success") {
                    rates = response.conversion_rates
                    currencies = response.conversion_rates.keys.toList()
                }
                Log.d("mk040515", response.toString())
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d("mk040515", e.toString())
            } finally {
                isLoading = false
                Log.d("mk040515", "da vao final")
            }
        }
    }

    fun addConverterHistory(converterHistory: ConverterHistory) {
        viewModelScope.launch {
            converterHistoryRepository.insertConverterHistory(converterHistory)
        }
    }
}