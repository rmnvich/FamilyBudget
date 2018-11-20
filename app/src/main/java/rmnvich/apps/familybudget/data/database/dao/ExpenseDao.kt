package rmnvich.apps.familybudget.data.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Expense

@Dao
interface ExpenseDao {

    @Query("SELECT * FROM expense WHERE isPlannedExpense = :isPlanned ORDER BY categoryId DESC")
    fun getAllExpenses(isPlanned: Boolean): Flowable<List<Expense>>

    @Query("SELECT * FROM expense WHERE expenseId = :id")
    fun getExpenseById(id: Int): Single<Expense>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertExpense(expense: Expense)

    @Delete
    fun deleteExpense(expense: Expense)
}