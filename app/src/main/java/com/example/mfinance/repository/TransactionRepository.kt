package com.example.mfinance.repository

import com.example.mfinance.data.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    fun getTotalSpentBetweenWithNotEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double>

    fun getTotalSpentBetweenWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double>

    fun getDistinctCategoriesBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<String>>

    fun getFilteredTransactions(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

    fun getFilteredTransactionsWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

}