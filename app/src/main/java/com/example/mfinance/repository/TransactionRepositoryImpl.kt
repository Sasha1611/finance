package com.example.mfinance.repository

import com.example.mfinance.data.TransactionEntity
import com.example.mfinance.data.TransactionDao
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun insertTransaction(transactionEntity: TransactionEntity) = transactionDao.insert(transactionEntity)


    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) = transactionDao.delete(transactionEntity)

    override suspend fun updateTransaction(transactionEntity: TransactionEntity) = transactionDao.update(transactionEntity)


    override fun getAllTransactions(): Flow<List<TransactionEntity>> = transactionDao.getAllTransactions()
}