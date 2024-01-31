package com.example.mfinance.presentation.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.components.BudgetCard
import com.example.mfinance.presentation.components.ListOfTransactions
import com.example.mfinance.presentation.components.TransactionFilterRow
import com.example.mfinance.presentation.transaction.TransactionViewModel
import com.example.mfinance.ui.theme.MFinanceTheme


@Composable
fun TransactionScreen(
    onAddClick: () -> Unit = {},
    viewModel: TransactionViewModel = viewModel(factory = AppViewModelProvider.Factory),
) {
    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            BudgetCard()
            TransactionFilterRow(
                filterUiState = viewModel.filterUiState,
                categories = viewModel.categoriesFlow.collectAsState().value
            ) {
                viewModel.updateFilterUiState(it)
            }
            ListOfTransactions(
                viewModel.getAllTransaction().collectAsState().value,
                enableAddingTransaction = true,
                onAddClick = {
                    onAddClick()
                })
        }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun GreetingPreview() {
    MFinanceTheme {
        TransactionScreen()
    }
}
