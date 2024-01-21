package com.example.mfinance.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.mfinance.util.LocalDateTimeConverter

@Database(entities = [TransactionEntity::class], version = 1, exportSchema = false)
@TypeConverters(LocalDateTimeConverter::class)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object {
        @Volatile
        private var Instance: TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    TransactionDatabase::class.java,
                    "transaction_database"
                )
                    .build().also { Instance = it }
            }
        }
    }
}