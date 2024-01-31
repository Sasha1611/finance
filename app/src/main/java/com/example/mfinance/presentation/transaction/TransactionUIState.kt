package com.example.mfinance.presentation.transaction

import java.time.LocalDateTime

data class TransactionUIState(
    val id: Int = 0,
    var amount: String = "",
    var type: String = "",
    var category: String = "",
    var time: LocalDateTime = LocalDateTime.now()
)
