package com.nguyenminhkhang.moneymind.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.nguyenminhkhang.moneymind.data.local.dao.ConverterHistoryDAO
import com.nguyenminhkhang.moneymind.data.local.dao.TransactionCategoryDAO
import com.nguyenminhkhang.moneymind.data.local.model.ConverterHistory
import com.nguyenminhkhang.moneymind.data.local.dao.TransactionDAO
import com.nguyenminhkhang.moneymind.data.local.model.Transaction
import com.nguyenminhkhang.moneymind.data.local.model.TransactionCategory

@Database (entities = [Transaction::class, ConverterHistory::class, TransactionCategory::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDAO
    abstract fun converterhistoryDao(): ConverterHistoryDAO
    abstract fun transactionCategoryDao(): TransactionCategoryDAO

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "transaction_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}