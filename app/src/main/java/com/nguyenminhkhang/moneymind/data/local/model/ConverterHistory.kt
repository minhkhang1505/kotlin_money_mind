package com.nguyenminhkhang.moneymind.data.local.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "converter_history")
data class ConverterHistory(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val unit: String,
    val valueToConvert: String,
    val unitIsConverted: String,
    val valueAfterConverted: String
)
