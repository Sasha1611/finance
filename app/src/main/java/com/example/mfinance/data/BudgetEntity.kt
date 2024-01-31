package com.example.mfinance.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class BudgetEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val amount: Double,
    val amountToSave: Double,
    val month: Int,
    val year: Int
)
