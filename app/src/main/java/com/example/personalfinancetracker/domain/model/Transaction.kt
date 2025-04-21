package com.example.personalfinancetracker.domain.model

data class Transaction(
    val id: Long = 0,
    val title: String,
    val amount: Double,
    val timestamp: Long,
    val type: TransactionType
)

enum class TransactionType {
    INCOME, EXPENSE
}
