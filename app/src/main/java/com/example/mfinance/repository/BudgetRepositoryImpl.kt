package com.example.mfinance.repository

import com.example.mfinance.data.BudgetEntity
import com.example.mfinance.data.BudgetDao
import kotlinx.coroutines.flow.Flow

class BudgetRepositoryImpl(private val budgetDao: BudgetDao): BudgetRepository {
    override suspend fun insert(budgetEntity: BudgetEntity) = budgetDao.insert(budgetEntity)

    override suspend fun update(budgetEntity: BudgetEntity) = budgetDao.update(budgetEntity)


    override suspend fun delete(budgetEntity: BudgetEntity) = budgetDao.delete(budgetEntity)


    override fun getBudgetFor(year: Int, month: Int): Flow<BudgetEntity?> = budgetDao.getBudgetFor(year, month)

}