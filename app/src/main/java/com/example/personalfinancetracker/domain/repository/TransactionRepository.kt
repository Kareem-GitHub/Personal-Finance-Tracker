package com.example.personalfinancetracker.domain.repository

import com.example.personalfinancetracker.domain.model.Transaction
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun addTransaction(transaction: Transaction)
    suspend fun deleteTransaction(transaction: Transaction)
    suspend fun getAllTransactions(): Flow<List<Transaction>>
}