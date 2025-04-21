import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.example.personalfinancetracker.presentation.viewmodel.TransactionViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeAppBar(
    onSortSelected: (TransactionViewModel.SortOption) -> Unit,
    onFilterSelected: (TransactionViewModel.FilterOption) -> Unit,
    currentFilter: TransactionViewModel.FilterOption
) {
    var menuExpanded by remember { mutableStateOf(false) }
    var showSortDialog by remember { mutableStateOf(false) }
    var showFilterDialog by remember { mutableStateOf(false) }

    CenterAlignedTopAppBar(
        title = {
            Text(
                text = "Personal Finance Tracker",
                modifier = Modifier.fillMaxWidth(),
                textAlign = TextAlign.Center
            )
        },
        actions = {
            IconButton(onClick = { menuExpanded = true }) {
                Icon(Icons.Default.MoreVert, contentDescription = "Menu")
            }

            DropdownMenu(
                expanded = menuExpanded,
                onDismissRequest = { menuExpanded = false }
            ) {
                DropdownMenuItem(
                    text = { Text("Filter") },
                    onClick = {
                        showFilterDialog = true
                        menuExpanded = false
                    }
                )
                DropdownMenuItem(
                    text = { Text("Sort") },
                    onClick = {
                        showSortDialog = true
                        menuExpanded = false
                    }
                )
            }

            if (showSortDialog) {
                SortDialog(
                    onDismiss = { showSortDialog = false },
                    onOptionSelected = {
                        onSortSelected(it)
                        showSortDialog = false
                    }
                )
            }

            if (showFilterDialog) {
                FilterDialog(
                    selectedOption = currentFilter,
                    onDismiss = { showFilterDialog = false },
                    onOptionSelected = {
                        onFilterSelected(it)
                        showFilterDialog = false
                    },
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )
}

