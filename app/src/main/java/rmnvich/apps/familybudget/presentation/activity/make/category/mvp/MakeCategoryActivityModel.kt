package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl

class MakeCategoryActivityModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        MakeCategoryActivityContract.Model {

    override fun getCategoryById(id: Int): Single<Category> {
        return databaseRepositoryImpl.getCategoryById(id)
    }

    override fun insertCategory(category: Category): Completable {
        return databaseRepositoryImpl.insertCategory(category)
    }
}