package rmnvich.apps.familybudget.presentation.fragment.categories.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl

class FragmentCategoriesModel(private val databaseRepositoryImpl: DatabaseRepositoryImpl) :
        FragmentCategoriesContract.Model {

    override fun getAllCategories(): Flowable<List<Category>> {
        return databaseRepositoryImpl.getAllCategories()
    }
}