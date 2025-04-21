package com.example.personalfinancetracker.domain.usecase

import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.repository.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetAllTransactionsUseCase(private val repository: TransactionRepository) {
    suspend operator fun invoke(): Flow<List<Transaction>> {
        return repository.getAllTransactions()
    }
}