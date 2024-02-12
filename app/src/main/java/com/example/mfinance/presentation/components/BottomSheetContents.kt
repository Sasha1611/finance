package com.example.mfinance.presentation.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.DisplayMode
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.mfinance.presentation.transaction.TransactionViewModel.FilterUiState

@Composable
fun AmountBottomSheetContent(
    modifier: Modifier = Modifier,
    filterUiState: () -> FilterUiState,
    onApplyClick: (FilterUiState) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var from by remember {
            if (filterUiState().amountFrom.toString() == "0") {
                mutableStateOf("")
            } else {
                mutableStateOf(filterUiState().amountFrom.toString())
            }
        }
        var to by remember {
            if (filterUiState().amountTo.toString() == "0") {
                mutableStateOf("")
            } else {
                mutableStateOf(filterUiState().amountTo.toString())
            }
        }
        Text(text = "Amount")
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            OutlinedTextField(
                modifier = Modifier.width(120.dp),
                value = from,
                onValueChange = {
                    from = it
                },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "From") },
            )
            HorizontalDivider(
                modifier = Modifier.width(20.dp)
            )
            OutlinedTextField(
                modifier = Modifier.width(120.dp),
                value = to,
                onValueChange = {
                    to = it
                },
                shape = RoundedCornerShape(10.dp),
                keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                label = { Text(text = "To") },
            )
        }
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = {
                var fromValue = from.toLongOrNull() ?: 0
                var toValue = to.toLongOrNull() ?: 0
                if (fromValue > toValue) {
                    fromValue = toValue.coerceAtMost(fromValue)
                    toValue = from.toLongOrNull() ?: 0
                }
                onApplyClick(
                    FilterUiState(amountFrom = fromValue, amountTo = toValue)
                )
            }) {
            Text(text = "Apply")
        }
    }
}

@Stable
@Composable
fun CategoryBottomSheetContent(
    filterUiState: () -> FilterUiState,
    categories: () -> List<String>,
    onApplyClick: (FilterUiState) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var selectedCategories by remember {
            mutableStateOf(filterUiState().categories)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Categories")
        }
        CategoriesLazyRow(
            { categories() },
            selectedCategories = { selectedCategories },
            onCategoryAdded = {
                selectedCategories = selectedCategories + it
            },
            onCategoryRemoved = {
                selectedCategories = selectedCategories.toMutableList().apply { remove(it) }
            })
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = {
                onApplyClick(
                    FilterUiState(categories = selectedCategories)
                )
            }) {
            Text(text = "Apply")
        }
    }
}

@Stable
@Composable
fun CategoriesLazyRow(
    categories: () -> List<String>,
    selectedCategories: () -> List<String>,
    onCategoryAdded: (String) -> Unit,
    onCategoryRemoved: (String) -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.Start) {
        items(categories(), key = { category -> category }) { category ->
            CategoriesCheckRow(
                category,
                isChecked = category in selectedCategories(),
                onCheckChanged = {
                    if (it) {
                        onCategoryAdded(category)
                    } else {
                        onCategoryRemoved(category)
                    }
                })
            HorizontalDivider()
        }
    }
}

@Composable
fun CategoriesCheckRow(
    category: String,
    isChecked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Checkbox(checked = isChecked, onCheckedChange = onCheckChanged)
        Text(text = category)
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountBottomSheetContentPrev() {
    AmountBottomSheetContent(filterUiState = { FilterUiState() })
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DateBottomSheetContent(
    filterUiState: () -> FilterUiState,
    onApplyClick: (FilterUiState) -> Unit
) {
    val dateRangePickerState = rememberDateRangePickerState(
        initialSelectedStartDateMillis = filterUiState().timeFrom,
        initialSelectedEndDateMillis = filterUiState().timeTo,
        initialDisplayMode = DisplayMode.Input
    )
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
            Text(text = "Time range")
        }
        DateRangePicker(
            state = dateRangePickerState,
            title = {},
            headline = {},
            showModeToggle = false
        )
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.secondaryContainer,
                contentColor = MaterialTheme.colorScheme.onSecondary
            ),
            onClick = {
                onApplyClick(
                    FilterUiState(
                        timeFrom = dateRangePickerState.selectedStartDateMillis
                            ?: filterUiState().timeFrom,
                        timeTo = dateRangePickerState.selectedEndDateMillis
                            ?: filterUiState().timeTo
                    )
                )
            }) {
            Text(text = "Apply")
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun CategoryBottomSheetContentPrev() {
    CategoryBottomSheetContent(
        filterUiState = {
            FilterUiState(
                categories = listOf(
                    "Electronics",
                    "Food",
                    "Clothes",
                )
            )
        }, categories = {
            listOf(
                "Electronics",
                "Food",
                "Clothes",
                "Services",
                "Transaction"
            )
        }
    )
}

@Preview
@Composable
private fun DateBottomSheetContentPrev() {

}