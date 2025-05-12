package com.example.budgetwiseapp.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.budgetwiseapp.data.entities.Category
import com.example.budgetwiseapp.data.entities.Expense
import com.example.budgetwiseapp.data.dao.CategoryDao
import com.example.budgetwiseapp.data.dao.ExpenseDao


@Database(entities = [Category::class, Expense::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun categoryDao(): CategoryDao
    abstract fun expenseDao(): ExpenseDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "budgetwise_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
