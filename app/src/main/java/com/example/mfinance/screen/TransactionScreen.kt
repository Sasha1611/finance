package com.example.mfinance.screen

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.ui.Alignment
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mfinance.components.BudgetCard
import com.example.mfinance.components.TransactionItem
import com.example.mfinance.data.Transaction
import com.example.mfinance.ui.theme.MFinanceTheme
import java.time.LocalDateTime

@Composable
fun TransactionScreen() {
    Scaffold{ paddings ->
        Column(modifier = Modifier.fillMaxSize().padding(paddings)) {
            BudgetCard()
            ListOfTransactions()
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    MFinanceTheme {
        ListOfTransactions()
    }
}

@Composable
fun ListOfTransactions() {
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .background(color = MaterialTheme.colorScheme.surface),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp)
        ) {
            items(getTestTransaction(), key = { transaction -> transaction.id }) { transaction ->
                TransactionItem(transaction = transaction)
            }
        }

        FloatingActionButton(
            onClick = { /*Click Implementation*/ },
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

fun getTestTransaction(): List<Transaction> {
    return listOf(
        Transaction(1, 460.00, "Food", "Zabka", LocalDateTime.now()),
        Transaction(2, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        Transaction(3, 25.00, "Car", "Stacja", LocalDateTime.now()),
        Transaction(4, 460.00, "Food", "Zabka", LocalDateTime.now()),
        Transaction(5, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        Transaction(6, 25.00, "Car", "Stacja", LocalDateTime.now()),
        Transaction(7, 460.00, "Food", "Zabka", LocalDateTime.now()),
        Transaction(8, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        Transaction(9, 25.00, "Car", "Stacja", LocalDateTime.now()),
        Transaction(10, 460.00, "Food", "Zabka", LocalDateTime.now()),
        Transaction(11, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        Transaction(12, 25.00, "Car", "Stacja", LocalDateTime.now()),
        Transaction(13, 460.00, "Food", "Zabka", LocalDateTime.now()),
        Transaction(14, -34.00, "Bank", "Millenium", LocalDateTime.now()),
        Transaction(15, 25.00, "Car", "Stacja", LocalDateTime.now())
    )
}
