package com.example.mfinance.data

import java.time.LocalDateTime

data class Transaction(
    val id: Int,
    val amount: Double,
    val type: String,
    val category: String,
    val time: LocalDateTime
)
