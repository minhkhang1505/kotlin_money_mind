package com.nguyenminhkhang.moneymind.data.model

import android.view.SurfaceControl
import java.util.UUID


data class Transaction(
    val id: String,
    val title: String,
    val amount: Double,
    val date: Long?,
    val time: String,
    val description: String?,
    val category: String
) {
    companion object {
        fun create (
            title: String,
            amount: Double,
            date: Long?,
            time: String,
            description: String,
            category: String
        ) : Transaction {
            return Transaction(
                id = UUID.randomUUID().toString(),
                title = title,
                amount = amount,
                date = date,
                time = time,
                description = description,
                category = category
            )
        }
    }
}
