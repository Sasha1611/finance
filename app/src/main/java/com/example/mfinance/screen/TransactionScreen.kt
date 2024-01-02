package com.example.mfinance.screen

import android.content.res.Configuration
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.mfinance.components.BudgetCard
import com.example.mfinance.components.ListOfTransactions
import com.example.mfinance.components.TransactionFilterRow
import com.example.mfinance.ui.theme.MFinanceTheme

@Composable
fun TransactionScreen() {
    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings)
        ) {
            BudgetCard()
            TransactionFilterRow()
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
