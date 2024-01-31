package com.example.mfinance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface TransactionDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(transactionEntity: TransactionEntity)

    @Update
    suspend fun update(transactionEntity: TransactionEntity)

    @Delete
    suspend fun delete(transactionEntity: TransactionEntity)

    @Query("SELECT * from transactions WHERE time BETWEEN :firstDayOfMonth AND :lastDayOfMonth ORDER BY id ASC")
    fun getAllTransactionsBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<TransactionEntity>>

    @Query("SELECT DISTINCT category from transactions WHERE time BETWEEN :firstDayOfMonth AND :lastDayOfMonth ORDER BY category ASC")
    fun getDistinctCategoriesBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<String>>

    @Query("SELECT SUM(amount) FROM transactions WHERE time BETWEEN :firstDayOfMonth AND :lastDayOfMonth")
    fun getTotalSpentBetween(firstDayOfMonth: Long, lastDayOfMonth: Long): Flow<Double>

    @Query("SELECT * FROM transactions WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(category IN (:categories)) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getFilteredTransactionsWithNotEmptyCategories(
        amountFrom: Int,
        amountTo: Int,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

    @Query("SELECT * FROM transactions WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getFilteredTransactionsWithEmptyCategories(
        amountFrom: Int,
        amountTo: Int,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

    @Query("Delete From transactions")
    fun deleteAll()

}