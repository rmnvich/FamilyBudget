package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class MakeCategoryActivityModel(private val databaseRepository: IDatabaseRepository) :
        MakeCategoryActivityContract.Model {

    override fun getCategoryById(id: Int): Single<Category> {
        return databaseRepository.getCategoryById(id)
    }

    override fun insertCategory(category: Category): Completable {
        return databaseRepository.insertCategory(category)
    }

    override fun deleteCategory(id: Int): Completable {
        return databaseRepository.getCategoryById(id)
                .flatMapCompletable {
                    databaseRepository.deleteCategory(it)
                }
    }
}