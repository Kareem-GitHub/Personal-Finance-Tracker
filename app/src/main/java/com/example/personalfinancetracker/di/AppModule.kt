package com.example.personalfinancetracker.di

import android.app.Application
import androidx.room.Room
import com.example.personalfinancetracker.data.local.TransactionDao
import com.example.personalfinancetracker.data.local.TransactionDatabase
import com.example.personalfinancetracker.data.repository.TransactionRepositoryImpl
import com.example.personalfinancetracker.domain.repository.TransactionRepository
import com.example.personalfinancetracker.domain.usecase.AddTransactionUseCase
import com.example.personalfinancetracker.domain.usecase.DeleteTransactionUseCase
import com.example.personalfinancetracker.domain.usecase.GetAllTransactionsUseCase
import com.example.personalfinancetracker.domain.usecase.TransactionUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDatabase(app: Application): TransactionDatabase {
        return Room.databaseBuilder(
            app,
            TransactionDatabase::class.java,
            "transaction_database"
        ).build()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(db: TransactionDatabase) = db.transactionDao()

    @Provides
    @Singleton
    fun provideTransactionRepository(dao: TransactionDao): TransactionRepository {
        return TransactionRepositoryImpl(dao)
    }

    @Provides
    @Singleton
    fun provideTransactionUseCases(repository: TransactionRepository): TransactionUseCases {
        return TransactionUseCases(
            addTransaction = AddTransactionUseCase(repository),
            deleteTransaction = DeleteTransactionUseCase(repository),
            getAllTransactions = GetAllTransactionsUseCase(repository)
        )
    }
}
