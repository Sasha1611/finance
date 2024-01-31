package com.example.mfinance.repository

import com.example.mfinance.data.BudgetEntity
import kotlinx.coroutines.flow.Flow

interface BudgetRepository {

    suspend fun insert(budgetEntity: BudgetEntity)

    suspend fun update(budgetEntity: BudgetEntity)

    suspend fun delete(budgetEntity: BudgetEntity)


    fun getBudgetFor(year: Int, month: Int): Flow<BudgetEntity?>
}