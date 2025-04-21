package com.example.personalfinancetracker.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.model.TransactionType

@Composable
fun TransactionInputForm(
    onSubmit: (Transaction) -> Unit,
    onDismiss: () -> Unit
) {
    var title by remember { mutableStateOf("") }
    var amount by remember { mutableStateOf("") }
    var type by remember { mutableStateOf(TransactionType.EXPENSE) }

    var titleError by remember { mutableStateOf<String?>(null) }
    var amountError by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Text(text = "Add Transaction", style = MaterialTheme.typography.titleLarge)

        Spacer(modifier = Modifier.height(16.dp))

        OutlinedTextField(
            value = title,
            onValueChange = {
                title = it
                titleError = null
            },
            label = { Text("Title") },
            isError = titleError != null,
            modifier = Modifier.fillMaxWidth()
        )
        if (titleError != null) {
            Text(text = titleError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = amount,
            onValueChange = {
                amount = it
                amountError = null
            },
            label = { Text("Amount") },
            isError = amountError != null,
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            )
        )
        if (amountError != null) {
            Text(text = amountError!!, color = MaterialTheme.colorScheme.error)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            TransactionType.entries.forEach {
                FilterChip(
                    selected = type == it,
                    onClick = { type = it },
                    label = { Text(it.name) }
                )
            }
        }

        Spacer(modifier = Modifier.height(16.dp))

        Row(
            horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedButton(onClick = onDismiss) {
                Text("Cancel")
            }

            Button(onClick = {
                var valid = true

                if (title.isBlank()) {
                    titleError = "Title cannot be empty"
                    valid = false
                }

                val parsedAmount = amount.toDoubleOrNull()
                if (parsedAmount == null || parsedAmount <= 0.0) {
                    amountError = "Enter a valid positive amount"
                    valid = false
                }

                if (valid) {
                    val signedAmount = if (type == TransactionType.EXPENSE) -parsedAmount!! else parsedAmount!!
                    val transaction = Transaction(
                        title = title.trim(),
                        amount = signedAmount,
                        timestamp = System.currentTimeMillis(),
                        type = type
                    )
                    onSubmit(transaction)
                    onDismiss()
                }
            }) {
                Text("Add")
            }

        }
    }
}
