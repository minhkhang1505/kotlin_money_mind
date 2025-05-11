package com.nguyenminhkhang.moneymind.data.local.model

import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "transaction_categories",
    indices = [Index(value = ["name"], unique = true)]
)
data class TransactionCategory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val type: String, //income or expense
    val name: String,
    val icon: String? = null,
    val color: String? = null,
)