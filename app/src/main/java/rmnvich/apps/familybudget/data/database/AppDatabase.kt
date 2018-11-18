package rmnvich.apps.familybudget.data.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import rmnvich.apps.familybudget.data.database.dao.*
import rmnvich.apps.familybudget.data.entity.*

@Database(entities = [Balance::class, Category::class, Expense::class,
    Income::class, User::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun balanceDao(): BalanceDao

    abstract fun categoryDao(): CategoryDao

    abstract fun expenseDao(): ExpenseDao

    abstract fun incomeDao(): IncomeDao

    abstract fun userDao(): UserDao

    abstract fun incomeTypeDao(): IncomeTypeDao
}