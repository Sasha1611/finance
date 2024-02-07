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

    override fun getTotalSpentBetweenWithNotEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double> =
        transactionDao.getTotalSpentBetweenWithNotEmptyCategories(
            amountFrom,
            amountTo,
            categories,
            timeFrom,
            timeTo
        )

    override fun getTotalSpentBetweenWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double> =
        transactionDao.getTotalSpentBetweenWithEmptyCategories(
            amountFrom,
            amountTo,
            timeFrom,
            timeTo
        )


    override fun getDistinctCategoriesBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<String>> =
        transactionDao.getDistinctCategoriesBetween(firstDayOfMonth, lastDayOfMonth)

    override fun getFilteredTransactions(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>> =
        transactionDao.getFilteredTransactionsWithNotEmptyCategories(
            amountFrom,
            amountTo,
            categories,
            timeFrom,
            timeTo
        )

    override fun getFilteredTransactionsWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ) =
        transactionDao.getFilteredTransactionsWithEmptyCategories(
            amountFrom,
            amountTo,
            timeFrom,
            timeTo
        )
}