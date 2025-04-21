package com.example.personalfinancetracker.presentation.viewmodel

import com.example.personalfinancetracker.domain.model.Transaction

data class TransactionState(
    val transactions: List<Transaction> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
