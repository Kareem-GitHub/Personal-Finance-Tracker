package com.example.personalfinancetracker.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room.databaseBuilder
import androidx.room.RoomDatabase

@Database(entities = [TransactionEntity::class], version = 1,exportSchema = false)
abstract class TransactionDatabase : RoomDatabase() {
    abstract fun transactionDao(): TransactionDao

    companion object{
        @Volatile
        private var INSTANCE:TransactionDatabase? = null

        fun getDatabase(context: Context): TransactionDatabase{
            return INSTANCE ?: synchronized(this) {
                val instance = databaseBuilder(
                    context.applicationContext,
                    TransactionDatabase::class.java,
                "transaction_databse"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}