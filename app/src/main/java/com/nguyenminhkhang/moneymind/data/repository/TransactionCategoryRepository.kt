package com.nguyenminhkhang.moneymind.data.repository

import com.nguyenminhkhang.moneymind.data.local.dao.TransactionCategoryDAO
import com.nguyenminhkhang.moneymind.data.local.model.TransactionCategory

class TransactionCategoryRepository(private val transactionCategoryDao: TransactionCategoryDAO) {
    fun getAllCategories() = transactionCategoryDao.getAllCategories()
    suspend fun insertCategory(category: TransactionCategory) = transactionCategoryDao.insert(category)
}