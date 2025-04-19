package com.nguyenminhkhang.moneymind.data.model

data class TransactionCategory(
    val type: String, //income or expense
    val name: String,
    val icon: String? = null,
    val color: String? = null,
)


var defaultCategories = listOf(
    TransactionCategory(type = "income", name = "Salary"),
    TransactionCategory(type = "income", name = "Award"),
    TransactionCategory(type = "income", name = "Other"),
    TransactionCategory(type = "expense", name = "Eat and drink"),
    TransactionCategory(type = "expense", name = "Entertainment"),
    TransactionCategory(type = "expense", name = "Shopping"),
    TransactionCategory(type = "expense", name = "Transport"),
    TransactionCategory(type = "expense", name = "Other"),
)