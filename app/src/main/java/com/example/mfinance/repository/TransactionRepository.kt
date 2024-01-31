package com.example.mfinance.repository

import com.example.mfinance.data.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    fun getAllTransactionsBetween(firstDayOfMonth: Long, lastDayOfMonth: Long): Flow<List<TransactionEntity>>

    fun getTotalSpentBetween(firstDayOfMonth: Long, lastDayOfMonth: Long): Flow<Double>

    fun getDistinctCategoriesBetween(firstDayOfMonth: Long, lastDayOfMonth: Long): Flow<List<String>>

    fun getFilteredTransactions(amountFrom: Int, amountTo: Int, categories: List<String>, timeFrom: Long, timeTo: Long): Flow<List<TransactionEntity>>

    fun getFilteredTransactionsWithEmptyCategories(amountFrom: Int, amountTo: Int, timeFrom: Long, timeTo: Long): Flow<List<TransactionEntity>>

}