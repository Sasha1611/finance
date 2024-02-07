package com.example.mfinance.presentation.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.budget.BudgetViewModel
import kotlinx.coroutines.launch


@Composable
fun BudgetScreen(
    modifier: Modifier = Modifier,
    budgetViewModel: BudgetViewModel = viewModel(factory = AppViewModelProvider.Factory)
) {
    val coroutineScope = rememberCoroutineScope()
    val budgetUiState = budgetViewModel.budgetFlow.collectAsStateWithLifecycle()
    var amount by remember(budgetUiState.value) {
        mutableStateOf(budgetUiState.value.amount.toString())
    }
    Scaffold { paddings ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddings),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            OutlinedTextField(
                value = amount,
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                onValueChange = {
                    amount = it
                },
                label = { Text(text = "Budget for current month") },
                colors = OutlinedTextFieldDefaults.colors(
                    focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                    disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                ),
                enabled = true,
                singleLine = true,
                isError = amount.toDoubleOrNull() == null
            )
            Spacer(modifier = modifier.height(16.dp))
            Button(
                onClick = {
                    budgetViewModel.updateBudgetUiState(budgetUiState.value.copy(amount = amount.toDouble()))
                    coroutineScope.launch {
                        budgetViewModel.saveBudget()
                    }
                },
                shape = MaterialTheme.shapes.small,
                modifier = Modifier.width(140.dp),
                enabled = amount.toDoubleOrNull() != null
            ) {
                Text("Save")
            }
        }
    }
}

@Preview
@Composable
private fun BudgetScreenPrev() {
    BudgetScreen()
}