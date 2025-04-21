package com.example.personalfinancetracker.data.repository

import com.example.personalfinancetracker.data.local.TransactionDao
import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.repository.TransactionRepository
import com.example.personalfinancetracker.utils.toDomain
import com.example.personalfinancetracker.utils.toEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionRepositoryImpl @Inject constructor(
    private val dao: TransactionDao
) :TransactionRepository {
    override suspend fun addTransaction(transaction: Transaction) {
        dao.addTransaction(transaction.toEntity())
    }

    override suspend fun deleteTransaction(transaction: Transaction) {
        dao.deleteTransaction(transaction.toEntity())
    }

    override suspend fun getAllTransactions(): Flow<List<Transaction>> {
       return dao.getAllTransaction().map { it.map { entity -> entity.toDomain() } }
    }
}