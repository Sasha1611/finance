package com.example.mfinance.presentation.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Category
import androidx.compose.material.icons.filled.DateRange
import androidx.compose.material.icons.outlined.FilterAlt
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun AmountChip(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    AssistChip(modifier = modifier, onClick = onClick, label = { Text(text = "Amount") })
}

@Composable
fun CategoryChip(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        label = { Text(text = "Category") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Category,
                contentDescription = "Category"
            )
        })
}

@Composable
fun DateChip(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        label = { Text(text = "Time") },
        leadingIcon = {
            Icon(imageVector = Icons.Default.DateRange, contentDescription = "Date range")
        })
}

@Composable
fun FilterChip(modifier: Modifier = Modifier, onClick: () -> Unit = {}) {
    AssistChip(
        modifier = modifier,
        onClick = onClick,
        label = { Text(text = "Filter") },
        leadingIcon = {
            Icon(imageVector = Icons.Outlined.FilterAlt, contentDescription = "Filter")
        })
}

