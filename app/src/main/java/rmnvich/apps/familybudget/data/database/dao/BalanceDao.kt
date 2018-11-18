package rmnvich.apps.familybudget.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance

@Dao
interface BalanceDao {

    @Query("SELECT * FROM balance WHERE id = :id")
    fun getBalance(id: Int): Single<Balance>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertBalance(balance: Balance)
}