package com.example.personalfinancetracker.domain.usecase

import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.repository.TransactionRepository

class AddTransactionUseCase (private val transactionRepository: TransactionRepository) {
    suspend operator fun invoke(transaction: Transaction) {
        transactionRepository.addTransaction(transaction)
    }
}