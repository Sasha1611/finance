package com.example.mfinance.presentation.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.TrendingDown
import androidx.compose.material.icons.automirrored.filled.TrendingUp
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.R
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.budget.BudgetUiState
import com.example.mfinance.presentation.budget.BudgetViewModel
import com.example.mfinance.presentation.transaction.TransactionViewModel
import com.example.mfinance.util.getLastDayOfCurrentMonth

@Composable
fun BudgetCard(
    transactionViewModel: TransactionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    budgetViewModel: BudgetViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val totalAmount = transactionViewModel.totalAmount.collectAsStateWithLifecycle()
    val budgetValue = budgetViewModel.budgetFlow.collectAsStateWithLifecycle()

    LazyRow(
        modifier = Modifier.background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(vertical = 8.dp, horizontal = 8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        item { TotalSpendCard(totalAmount.value) }
        item { TrendCard(budgetValue.value, totalAmount.value) }
        item { GoalCard() }
    }
}

@Preview(showBackground = true, uiMode = Configuration.UI_MODE_NIGHT_YES, widthDp = 600)
@Composable
fun BudgetCardPreview() {
    BudgetCard()
}

@Composable
fun TotalSpendCard(totalAmount: Double) {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = stringResource(R.string.total_spend),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = totalAmount.toString(),
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

@Composable
fun TrendCard(
    budgetValue: BudgetUiState,
    totalAmount: Double
) {
    val trend by remember(totalAmount, budgetValue) {
        mutableIntStateOf(evaluateDailyTrend(budgetUiState = budgetValue))
    }
    val isPositive by remember(trend) {
        mutableStateOf(
            isPositiveTrend(
                trendValue = trend,
                budgetUiState = budgetValue,
                totalSpentAmount = totalAmount
            )
        )
    }
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Day spent trend",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Row(verticalAlignment = Alignment.CenterVertically) {
                Text(
                    text = trend.toString(),
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    style = MaterialTheme.typography.titleLarge
                )
                Icon(
                    imageVector = if(isPositive) Icons.AutoMirrored.Filled.TrendingDown else Icons.AutoMirrored.Filled.TrendingUp,
                    contentDescription = "",
                    tint = MaterialTheme.colorScheme.onSecondaryContainer
                )
            }
        }
    }
}

@Composable
fun GoalCard() {
    Card(
        modifier = Modifier
            .width(160.dp)
            .height(128.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.secondaryContainer),
        shape = RoundedCornerShape(16.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize()
        ) {
            Text(
                text = "Goal to save",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleSmall
            )
            Text(
                text = "143",
                color = MaterialTheme.colorScheme.onSecondaryContainer,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}

private fun evaluateDailyTrend(budgetUiState: BudgetUiState): Int {
    return ((budgetUiState.amount - budgetUiState.amountToSave) / getLastDayOfCurrentMonth()).toInt()
}

private fun isPositiveTrend(
    trendValue: Int,
    budgetUiState: BudgetUiState,
    totalSpentAmount: Double
): Boolean {
    return ((budgetUiState.amount - budgetUiState.amountToSave + totalSpentAmount)).toInt() / getLastDayOfCurrentMonth() <= trendValue
}