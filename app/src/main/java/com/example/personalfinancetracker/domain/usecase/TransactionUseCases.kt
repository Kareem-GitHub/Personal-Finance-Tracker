package com.example.personalfinancetracker.domain.usecase

data class TransactionUseCases(
    val addTransaction: AddTransactionUseCase,
    val deleteTransaction: DeleteTransactionUseCase,
    val getAllTransactions: GetAllTransactionsUseCase
)
