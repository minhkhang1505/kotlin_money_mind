package com.nguyenminhkhang.moneymind.data.repository

import com.nguyenminhkhang.moneymind.data.local.Transaction
import com.nguyenminhkhang.moneymind.data.local.dao.TransactionDAO
import kotlinx.coroutines.flow.Flow

class TransactionRepository(private val transactionDao: TransactionDAO) {
    fun getAllTransactions() = transactionDao.getAllTransactions()
    suspend fun insertTransaction(transaction: Transaction) = transactionDao.insert(transaction)
    suspend fun deleteTransaction(transaction: Transaction) = transactionDao.delete(transaction)
}