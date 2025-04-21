package com.example.personalfinancetracker.presentation.ui.screens

import HomeAppBar
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.personalfinancetracker.presentation.viewmodel.TransactionViewModel
import com.example.personalfinancetracker.presentation.ui.components.TransactionItem
import com.example.personalfinancetracker.presentation.ui.components.TransactionInputForm
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: TransactionViewModel = viewModel()
) {
    val transactions by viewModel.filteredAndSortedTransactions.collectAsState()
    val scope = rememberCoroutineScope()
    var isBottomSheetVisible by remember { mutableStateOf(false) }
    val currentFilter = viewModel.filterOption.collectAsState().value

    val bottomSheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )

    Scaffold(

        topBar = {
            HomeAppBar(
                onSortSelected = { viewModel.updateSortOption(it) },
                onFilterSelected = { viewModel.updateFilterOption(it) },
                currentFilter = currentFilter
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                isBottomSheetVisible = true
                scope.launch { bottomSheetState.show() }
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Add Transaction")
            }
        }
    ) { paddingValues ->

        LazyColumn(contentPadding = paddingValues) {
            itemsIndexed(transactions) { _, transaction ->
                TransactionItem(transaction = transaction, onClick = {
                    viewModel.deleteTransaction(it)
                })
            }
        }

        if (isBottomSheetVisible) {
            ModalBottomSheet(
                onDismissRequest = {
                    isBottomSheetVisible = false
                    scope.launch { bottomSheetState.hide() }
                },
                sheetState = bottomSheetState
            ) {
                TransactionInputForm(
                    onSubmit = { transaction ->
                        viewModel.addTransaction(transaction)
                    },
                    onDismiss = {
                        isBottomSheetVisible = false
                        scope.launch { bottomSheetState.hide() }
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewHomeScreen() {
    HomeScreen()
}
