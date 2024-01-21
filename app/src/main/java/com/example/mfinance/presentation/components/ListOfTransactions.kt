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
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.data.TransactionEntity
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.transaction.TransactionUIState
import com.example.mfinance.presentation.transaction.TransactionViewModel
import java.time.LocalDateTime

@Composable
fun ListOfTransactions(transactions: List<TransactionUIState>, onClick: () -> Unit = {}) {
    Box(modifier = Modifier.fillMaxSize().heightIn(min = 300.dp)) {
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

        FloatingActionButton(
            onClick = { onClick() },
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

/*
fun getTestTransactions(): List<TransactionUIState> {
    return listOf(
        TransactionUIState(1, 460.00, "Food", "Zabka", LocalDateTime.now()),
        TransactionUIState(2, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        TransactionUIState(3, 25.00, "Car", "Stacja", LocalDateTime.now()),
        TransactionUIState(4, 460.00, "Food", "Zabka", LocalDateTime.now()),
        TransactionUIState(5, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        TransactionUIState(6, 25.00, "Car", "Stacja", LocalDateTime.now()),
        TransactionUIState(7, 460.00, "Food", "Zabka", LocalDateTime.now()),
        TransactionUIState(8, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        TransactionUIState(9, 25.00, "Car", "Stacja", LocalDateTime.now()),
        TransactionUIState(10, 460.00, "Food", "Zabka", LocalDateTime.now()),
        TransactionUIState(11, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        TransactionUIState(12, 25.00, "Car", "Stacja", LocalDateTime.now()),
        TransactionUIState(13, 460.00, "Food", "Zabka", LocalDateTime.now()),
        TransactionUIState(14, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        TransactionUIState(15, 25.00, "Car", "Stacja", LocalDateTime.now())
    )
}*/
