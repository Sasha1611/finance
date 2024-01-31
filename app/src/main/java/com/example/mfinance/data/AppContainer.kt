package com.example.mfinance.data

import android.content.Context
import com.example.mfinance.repository.BudgetRepository
import com.example.mfinance.repository.BudgetRepositoryImpl
import com.example.mfinance.repository.TransactionRepository
import com.example.mfinance.repository.TransactionRepositoryImpl

interface AppContainer {
    val transactionRepository: TransactionRepository

    val budgetRepository : BudgetRepository
}


class AppDataContainer(private val context: Context) : AppContainer {

    override val transactionRepository: TransactionRepository by lazy {
        TransactionRepositoryImpl(TransactionDatabase.getDatabase(context).transactionDao())
    }

    override val budgetRepository: BudgetRepository by lazy {
        BudgetRepositoryImpl(TransactionDatabase.getDatabase(context).budgetDao())
    }
}