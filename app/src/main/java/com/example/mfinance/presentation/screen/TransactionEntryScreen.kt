package com.example.mfinance.presentation.screen


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material3.Button
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.mfinance.presentation.AppViewModelProvider
import com.example.mfinance.presentation.components.TransactionEntryTopBar
import com.example.mfinance.presentation.transaction.TransactionUIState
import com.example.mfinance.presentation.transaction.TransactionViewModel
import com.example.mfinance.util.getFormattedDate
import com.example.mfinance.util.toLocalDateTime
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionEntryScreen(
    viewModel: TransactionViewModel = viewModel(factory = AppViewModelProvider.Factory),
    navigateUp: () -> Unit = {},
    navigateBack: () -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()
    Scaffold(topBar = {
        TransactionEntryTopBar(
            title = "Add transaction",
            navigateUp = navigateUp
        )
    }) { innerPadding ->
        TransactionEntryBody(
            viewModel.transactionUiState,
            modifier = Modifier.padding(innerPadding),
            onSaveClick = {
                coroutineScope.launch {
                    viewModel.saveTransaction()
                    navigateBack()
                }
            },
            onTransactionValueChanged = { viewModel.updateTransactionUiState(it) }
        )
    }
}

@Composable
fun TransactionEntryBody(
    transactionUIState: TransactionUIState,
    modifier: Modifier = Modifier,
    onSaveClick: () -> Unit,
    onTransactionValueChanged: (TransactionUIState) -> Unit
) {
    Column(
        verticalArrangement = Arrangement.spacedBy(20.dp),
        modifier = modifier.padding(16.dp)
    ) {
        TransactionInputForm(
            transactionUIState = transactionUIState,
            onValueChanged = onTransactionValueChanged
        )
        Button(
            onClick = onSaveClick,
            shape = MaterialTheme.shapes.small,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Save")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TransactionInputForm(
    transactionUIState: TransactionUIState,
    modifier: Modifier = Modifier,
    onValueChanged: (TransactionUIState) -> Unit = {}
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        OutlinedTextField(
            value = transactionUIState.amount,
            onValueChange = { onValueChanged(transactionUIState.copy(amount = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
            label = { Text(text = "Amount") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
        OutlinedTextField(
            value = transactionUIState.type,
            onValueChange = { onValueChanged(transactionUIState.copy(type = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Type") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
        OutlinedTextField(
            value = transactionUIState.category,
            onValueChange = { onValueChanged(transactionUIState.copy(category = it)) },
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Category") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            modifier = Modifier.fillMaxWidth(),
            enabled = true,
            singleLine = true
        )
        var showDatePicker by remember { mutableStateOf(false) }
        val datePickerState = rememberDatePickerState(initialDisplayMode = DisplayMode.Picker)
        OutlinedTextField(
            value = datePickerState.selectedDateMillis?.let { getFormattedDate(it) } ?: "",
            onValueChange = {},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            label = { Text(text = "Time") },
            colors = OutlinedTextFieldDefaults.colors(
                focusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                unfocusedContainerColor = MaterialTheme.colorScheme.secondaryContainer,
                disabledContainerColor = MaterialTheme.colorScheme.secondaryContainer,
            ),
            trailingIcon = {
                IconButton(onClick = { showDatePicker = true }) {
                    Icon(imageVector = Icons.Default.DateRange, contentDescription = "")
                }
            },
            readOnly = true,
            modifier = Modifier.fillMaxWidth(),
        )
        if (showDatePicker) {
            DatePickerDialog(
                onDismissRequest = { /*TODO*/ },
                confirmButton = {
                    Button(onClick = {
                        datePickerState.selectedDateMillis?.toLocalDateTime()?.let {
                            onValueChanged(
                                transactionUIState.copy(
                                    time = it
                                )
                            )
                        }
                        showDatePicker = false
                    }) {
                        Text(text = "OK")
                    }
                },
                dismissButton = {
                    Button(onClick = { /*TODO*/ }) {
                        Text(text = "Cancel")
                    }
                }
            ) {
                DatePicker(state = datePickerState)
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun TransactionDialogPrev() {
    TransactionEntryBody(
        transactionUIState = TransactionUIState(0, "20.0", "Type", "Category"),
        onSaveClick = {}, onTransactionValueChanged = {})
}