package com.nguyenminhkhang.moneymind.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.nguyenminhkhang.moneymind.data.local.model.ConverterHistory
import kotlinx.coroutines.flow.Flow

@Dao
interface ConverterHistoryDAO{
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(converterHistory: ConverterHistory)

    @Query("SELECT * FROM converter_history ORDER BY id DESC")
    fun getAllConverterHistory(): Flow<List<ConverterHistory>>

    @Delete
    suspend fun delete(converterHistory: ConverterHistory)
}
