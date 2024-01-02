package com.example.mfinance.components

import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun TransactionFilterButton(modifier: Modifier = Modifier, sortingParam: String) {
    Button(
        onClick = { /*TODO*/ },
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.tertiaryContainer,
            contentColor = MaterialTheme.colorScheme.onTertiaryContainer
        ),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 4.dp),
        modifier = modifier
    ) {
        Text(text = sortingParam)
    }
}

@Preview
@Composable
private fun TransactionFilterButtonPrev() {
    TransactionFilterButton(sortingParam = "Products")
}

fun getListOfSortingParameters():List<String>{
    return listOf(
        "Products", "Transfer", "Services", "Closes", "Other"
    )
}