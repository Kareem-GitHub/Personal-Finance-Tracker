package com.example.personalfinancetracker.utils

import com.example.personalfinancetracker.data.local.TransactionEntity
import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.model.TransactionType

fun Transaction.toEntity() = TransactionEntity(
    id = id,
    title = title,
    amount = amount,
    type = type.name,
    timestamp = timestamp
)

fun TransactionEntity.toDomain() = Transaction(
    id = id,
    title = title,
    amount = amount,
    type = TransactionType.valueOf(type),
    timestamp = timestamp
)