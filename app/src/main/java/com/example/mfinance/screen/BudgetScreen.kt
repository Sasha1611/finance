package com.example.mfinance.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier


@Composable
fun BudgetScreen(modifier: Modifier = Modifier) {
    Scaffold { paddings ->
        Column(modifier = Modifier.fillMaxSize().padding(paddings)) {
            Text(text = "BudgetScreen")
        }
    }
}