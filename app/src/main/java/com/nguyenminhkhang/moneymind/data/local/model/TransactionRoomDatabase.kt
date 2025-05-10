package com.nguyenminhkhang.moneymind.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID

@Entity(tableName = "transactions")
data class Transaction(
    @PrimaryKey val id: String = UUID.randomUUID().toString(),
    val title: String,
    val amount: Double,
    val date: Long?,
    val time: String,
    val description: String?,
    val category: String
)
