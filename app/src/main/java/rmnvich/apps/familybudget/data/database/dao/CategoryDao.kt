package rmnvich.apps.familybudget.data.database.dao

import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category

@Dao
interface CategoryDao {

    @Query("SELECT * FROM category ORDER BY categoryId DESC")
    fun getAllCategories(): Flowable<List<Category>>

    @Query("SELECT * FROM category WHERE categoryId = :id")
    fun getCategoryById(id: Int): Single<Category>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCategory(category: Category)
}