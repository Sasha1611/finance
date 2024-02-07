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

    @Query("SELECT DISTINCT category from transactions WHERE time BETWEEN :firstDayOfMonth AND :lastDayOfMonth ORDER BY category ASC")
    fun getDistinctCategoriesBetween(
        firstDayOfMonth: Long,
        lastDayOfMonth: Long
    ): Flow<List<String>>

    @Query(
        "SELECT SUM(amount) FROM transactions  WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(category IN (:categories)) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getTotalSpentBetweenWithNotEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double>

    @Query(
        "SELECT SUM(amount) FROM transactions  WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getTotalSpentBetweenWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ): Flow<Double>

    @Query(
        "SELECT * FROM transactions WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(category IN (:categories)) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getFilteredTransactionsWithNotEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        categories: List<String>,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

    @Query(
        "SELECT * FROM transactions WHERE " +
                "(:amountFrom = 0 OR amount >= :amountFrom) AND " +
                "(:amountTo = 0 OR amount <= :amountTo) AND " +
                "(:timeFrom = 0 OR time >= :timeFrom) AND " +
                "(:timeTo = 0 OR time <= :timeTo)"
    )
    fun getFilteredTransactionsWithEmptyCategories(
        amountFrom: Long,
        amountTo: Long,
        timeFrom: Long,
        timeTo: Long
    ): Flow<List<TransactionEntity>>

    @Query("Delete From transactions")
    fun deleteAll()

}