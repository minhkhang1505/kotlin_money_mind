package com.nguyenminhkhang.moneymind.data.repository

import com.nguyenminhkhang.moneymind.data.local.model.ConverterHistory
import com.nguyenminhkhang.moneymind.data.local.dao.ConverterHistoryDAO

class ConverterHistoryRepository(private val converterHistoryDao: ConverterHistoryDAO) {
    fun getAllConverterHistories() = converterHistoryDao.getAllConverterHistory()
    suspend fun insertConverterHistory(converterHistory: ConverterHistory) = converterHistoryDao.insert(converterHistory)
    suspend fun deleteConverterHistory(converterHistory: ConverterHistory) = converterHistoryDao.delete(converterHistory)
}