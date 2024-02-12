package com.example.mfinance.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.transaction.TransactionViewModel

@Composable
fun ListOfTransactions(
    viewModel: TransactionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    enableAddingTransaction: Boolean = false,
    onAddClick: () -> Unit = {},
) {
    val transactions = viewModel.getAllTransaction().collectAsStateWithLifecycle().value
    Box(modifier = Modifier
        .fillMaxSize()
        .heightIn(min = 300.dp)) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
        ) {
            items(transactions, key = { transaction -> transaction.id }) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }
        if (enableAddingTransaction) {
            FloatingActionButton(
                onClick = { onAddClick() },
                containerColor = MaterialTheme.colorScheme.tertiaryContainer,
                contentColor = MaterialTheme.colorScheme.onTertiaryContainer,
                modifier = Modifier
                    .align(Alignment.BottomEnd)
                    .padding(16.dp),
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "edit",
                    modifier = Modifier.size(24.dp)
                )
            }
        }
    }
}