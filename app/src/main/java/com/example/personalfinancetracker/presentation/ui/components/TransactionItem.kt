package com.example.personalfinancetracker.presentation.ui.components

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.personalfinancetracker.domain.model.Transaction
import com.example.personalfinancetracker.domain.model.TransactionType
import java.text.SimpleDateFormat
import java.util.*

@Composable
fun TransactionItem(
    transaction: Transaction,
    onClick: (Transaction) -> Unit
) {
    // Format the timestamp into a readable date format
    val dateFormatter = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())
    val date = dateFormatter.format(Date(transaction.timestamp))

    val typeColor = when (transaction.type) {
        TransactionType.INCOME -> Color.Green
        TransactionType.EXPENSE -> Color.Red
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        elevation = CardDefaults.elevatedCardElevation(8.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Transaction Details
            Column(
                modifier = Modifier.weight(1f)
            ) {
                Text(text = "Title: ${transaction.title}")
                Text(text = "Amount: \$${"%.2f".format(transaction.amount)}")
                Text(text = "Date: $date")
                Text(text = "Type: ${transaction.type.name}", color = typeColor)
            }

            IconButton(onClick = { onClick(transaction) }) {
                Icon(imageVector = Icons.Default.Delete, contentDescription = "Delete")
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewTransactionItem() {
    val dummyTransaction = Transaction(
        id = 1,
        title = "Groceries",
        amount = 100.0,
        timestamp = System.currentTimeMillis(),
        type = TransactionType.EXPENSE
    )

    TransactionItem(transaction = dummyTransaction) {
        println("Deleted: ${it.title}")
    }
}

