package com.example.mfinance.presentation.transaction

import java.time.LocalDateTime

data class TransactionUIState(
    val id: Int = 0,
    var amount: Double = 0.0,
    var type: String = "",
    var category: String = "",
    var time: LocalDateTime = LocalDateTime.now()
)
