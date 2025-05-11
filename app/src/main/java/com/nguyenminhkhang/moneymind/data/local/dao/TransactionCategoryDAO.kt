package com.nguyenminhkhang.moneymind.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nguyenminhkhang.moneymind.data.local.model.TransactionCategory
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionCategoryDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(category: TransactionCategory)

    @Query("SELECT * FROM transaction_categories ORDER BY id DESC")
    fun getAllCategories(): Flow<List<TransactionCategory>>

}