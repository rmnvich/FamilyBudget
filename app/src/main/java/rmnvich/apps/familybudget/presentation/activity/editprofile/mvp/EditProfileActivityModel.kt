package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository

class EditProfileActivityModel(private val databaseRepository: IDatabaseRepository) :
        EditProfileActivityContract.Model {

    override fun getUserById(userId: Int): Single<User> {
        return databaseRepository.getUserById(userId)
    }

    override fun updateUserIncomeTypes(user: User): Completable {
        return databaseRepository.insertUser(user)
    }
}