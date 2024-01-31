package com.example.mfinance.repository

import com.example.mfinance.data.TransactionEntity
import com.example.mfinance.data.TransactionDao
import kotlinx.coroutines.flow.Flow

class TransactionRepositoryImpl(private val transactionDao: TransactionDao) :
    TransactionRepository {
    override suspend fun insertTransaction(transactionEntity: TransactionEntity) =
        transactionDao.insert(transactionEntity)

    override suspend fun deleteTransaction(transactionEntity: TransactionEntity) =
        transactionDao.delete(transactionEntity)

    override suspend fun updateTransaction(transactionEntity: TransactionEntity) =
        transactionDao.update(transactionEntity)

    override fun getAllTransactionsBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<TransactionEntity>> =
        transactionDao.getAllTransactionsBetween(firstDayOfMonth, lastDayOfMonth)

    override fun getTotalSpentBetween(firstDayOfMonth: Long, lastDayOfMonth: Long): Flow<Double> =
        transactionDao.getTotalSpentBetween(firstDayOfMonth, lastDayOfMonth)


    override fun getDistinctCategoriesBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<String>> =
        transactionDao.getDistinctCategoriesBetween(firstDayOfMonth, lastDayOfMonth)

    override fun getFilteredTransactions(
        amountFrom: Int,
        amountTo: Int,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>> =
        transactionDao.getFilteredTransactionsWithNotEmptyCategories(amountFrom, amountTo, categories, timeFrom, timeTo)

    override fun getFilteredTransactionsWithEmptyCategories(amountFrom: Int, amountTo: Int, timeFrom: Long, timeTo: Long) =
        transactionDao.getFilteredTransactionsWithEmptyCategories(amountFrom, amountTo, timeFrom, timeTo)
}