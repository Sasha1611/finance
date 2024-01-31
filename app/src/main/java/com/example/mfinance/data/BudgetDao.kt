package com.example.mfinance.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow

@Dao
interface BudgetDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(budgetEntity: BudgetEntity)

    @Update
    suspend fun update(budgetEntity: BudgetEntity)

    @Delete
    suspend fun delete(budgetEntity: BudgetEntity)

    @Query("Delete From budgetEntity")
    fun deleteAll()


    @Query("SELECT * from budgetEntity WHERE year = :year AND month = :month")
    fun getBudgetFor(year: Int, month: Int): Flow<BudgetEntity?>
}