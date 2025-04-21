package com.example.personalfinancetracker.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.usecase.TransactionUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionUseCases: TransactionUseCases
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<Transaction>>(emptyList())
    val transactions: StateFlow<List<Transaction>> = _transactions

    private val _filteredAndSortedTransactions = MutableStateFlow<List<Transaction>>(emptyList())
    val filteredAndSortedTransactions: StateFlow<List<Transaction>> = _filteredAndSortedTransactions

    private var currentSortOption = SortOption.DATE
    private var currentFilterOption = FilterOption.ALL

    private val _filterOption = MutableStateFlow(currentFilterOption)
    val filterOption: StateFlow<FilterOption> = _filterOption


    init {
        viewModelScope.launch {
            transactionUseCases.getAllTransactions().collect { list ->
                _transactions.value = list
                applySortAndFilter()
            }
        }
    }

    fun addTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionUseCases.addTransaction(transaction)
        }
    }

    fun deleteTransaction(transaction: Transaction) {
        viewModelScope.launch {
            transactionUseCases.deleteTransaction(transaction)
        }
    }

    fun updateSortOption(option: SortOption) {
        currentSortOption = option
        applySortAndFilter()
    }

    fun updateFilterOption(option: FilterOption) {
        currentFilterOption = option
        _filterOption.value = option
        applySortAndFilter()
    }

    private fun applySortAndFilter() {
        val filtered = when (currentFilterOption) {
            FilterOption.INCOME -> _transactions.value.filter { it.amount >= 0 }
            FilterOption.EXPENSE -> _transactions.value.filter { it.amount < 0 }
            FilterOption.ALL -> _transactions.value
        }

        val sorted = when (currentSortOption) {
            SortOption.TITLE -> filtered.sortedBy { it.title }
            SortOption.AMOUNT -> filtered.sortedBy { it.amount }
            SortOption.DATE -> filtered.sortedByDescending { it.timestamp } // or .sortedBy if needed
        }

        _filteredAndSortedTransactions.value = sorted
    }

    enum class SortOption {
        TITLE, AMOUNT, DATE
    }

    enum class FilterOption {
        ALL, INCOME, EXPENSE
    }
}
