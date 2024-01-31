package com.example.mfinance.presentation.budget

import java.time.LocalDateTime

data class BudgetUiState(
    val id: Int = 0,
    val amount: Double = 0.0,
    val amountToSave: Double = 0.0,
    val month: Int = LocalDateTime.now().monthValue,
    val year: Int = LocalDateTime.now().year
)
