package rmnvich.apps.familybudget.presentation.fragment.categories.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class FragmentCategoriesModel(private val databaseRepository: IDatabaseRepository) :
        FragmentCategoriesContract.Model {

    override fun getAllCategories(): Flowable<List<Category>> {
        return databaseRepository.getAllCategories()
    }
}