package com.example.mfinance.repository

import com.example.mfinance.data.TransactionEntity
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {

    suspend fun insertTransaction(transactionEntity: TransactionEntity)

    /**
     * Delete item from the data source
     */
    suspend fun deleteTransaction(transactionEntity: TransactionEntity)

    /**
     * Update item in the data source
     */
    suspend fun updateTransaction(transactionEntity: TransactionEntity)

    fun getAllTransactions(): Flow<List<TransactionEntity>>
}