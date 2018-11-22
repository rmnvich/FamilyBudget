package rmnvich.apps.familybudget.data.database.dao

import android.arch.persistence.room.*
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Income

@Dao
interface IncomeDao {

    @Query("SELECT * FROM income ORDER BY timestamp DESC")
    fun getAllIncomes(): Flowable<List<Income>>

    @Query("SELECT * FROM income WHERE timestamp >= :timeRangeStart AND timestamp <= :timeRangeEnd ORDER BY timestamp DESC")
    fun getSortedIncomes(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Income>>

    @Query("SELECT * FROM income WHERE incomeId = :id")
    fun getIncomeById(id: Int): Single<Income>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertIncome(income: Income)

    @Delete
    fun deleteIncome(income: Income)
}