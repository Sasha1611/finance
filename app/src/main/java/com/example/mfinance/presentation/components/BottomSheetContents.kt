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
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.runtime.Composable
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
    filterUiState: FilterUiState,
    onApplyClick: (FilterUiState) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        var from by remember {
            if (filterUiState.amountFrom.toString() == "0") {
                mutableStateOf("")
            } else {
                mutableStateOf(filterUiState.amountFrom.toString())
            }
        }
        var to by remember {
            if (filterUiState.amountTo.toString() == "0") {
                mutableStateOf("")
            } else {
                mutableStateOf(filterUiState.amountTo.toString())
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
            Divider(
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
                containerColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
            ),
            onClick = {
                var fromValue = from.toIntOrNull() ?: 0
                var toValue = to.toIntOrNull() ?: 0
                if (fromValue > toValue) {
                    fromValue = toValue.coerceAtMost(fromValue)
                    toValue = from.toIntOrNull() ?: 0
                }
                onApplyClick(
                    FilterUiState(amountFrom = fromValue, amountTo = toValue)
                )
            }) {
            Text(text = "Apply")
        }
    }
}

@Composable
fun CategoryBottomSheetContent(
    filterUiState: FilterUiState,
    categories: List<String>,
    onApplyClick: (FilterUiState) -> Unit = { }
) {
    Column(
        modifier = Modifier
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        var selectedCategories by remember {
            mutableStateOf(filterUiState.categories)
        }
        Row(horizontalArrangement = Arrangement.Center, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Categories")
        }
        CategoriesLazyRow(categories, selectedCategories = selectedCategories, onCategoryAdded = {
            selectedCategories = selectedCategories + it
        }, onCategoryRemoved = {
            selectedCategories = selectedCategories.toMutableList().apply { remove(it) }
        })
        Button(
            modifier = Modifier.fillMaxWidth(),
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colors.secondary,
                contentColor = MaterialTheme.colors.onSecondary
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

@Composable
fun CategoriesLazyRow(
    categories: List<String>,
    selectedCategories: List<String>,
    onCategoryAdded: (String) -> Unit,
    onCategoryRemoved: (String) -> Unit
) {
    LazyColumn(horizontalAlignment = Alignment.Start) {
        items(categories, key = { category -> category }) { category ->
            CategoriesCheckRow(
                category,
                selectedCategories = selectedCategories,
                onCategoryAdded = { onCategoryAdded(it) },
                onCategoryRemoved = { onCategoryRemoved(it) })
            Divider()
        }
    }
}

@Composable
fun CategoriesCheckRow(
    category: String,
    selectedCategories: List<String>,
    onCategoryAdded: (String) -> Unit = {},
    onCategoryRemoved: (String) -> Unit = {}
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        var isChecked by remember {
            mutableStateOf(selectedCategories.contains(category))
        }
        Checkbox(checked = isChecked, onCheckedChange = {
            isChecked = !isChecked
            if (isChecked) {
                onCategoryAdded(category)
            } else {
                onCategoryRemoved(category)
            }
        })
        Text(text = category)
    }
}

@Preview(showBackground = true)
@Composable
private fun AmountBottomSheetContentPrev() {
    AmountBottomSheetContent(filterUiState = FilterUiState())
}

@Preview(showBackground = true)
@Composable
private fun CategoryBottomSheetContentPrev() {
    CategoryBottomSheetContent(
        filterUiState = FilterUiState(
            categories = listOf(
                "Electronics",
                "Food",
                "Clothes",
            )
        ), categories = listOf(
            "Electronics",
            "Food",
            "Clothes",
            "Services",
            "Transaction"
        )
    )
}