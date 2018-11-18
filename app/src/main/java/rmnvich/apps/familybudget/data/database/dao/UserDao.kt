package rmnvich.apps.familybudget.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User

@Dao
interface UserDao {

    @Query("SELECT * FROM user ORDER BY userId ASC")
    fun getAllUsers(): Flowable<List<User>>

    @Query("SELECT * FROM user WHERE userId = :userId")
    fun getUserById(userId: Int): Single<User>

    @Query("SELECT * FROM user WHERE name = :name AND lastname = :lastname")
    fun getUserByName(name: String, lastname: String): Single<User>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertUser(user: User)
}