package rmnvich.apps.familybudget.presentation.activity.login.mvp

import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.database.IDatabaseRepository
import rmnvich.apps.familybudget.domain.interactor.preferences.IPreferencesRepository

class LoginActivityModel(private val databaseRepository: IDatabaseRepository,
                         private val preferencesRepository: IPreferencesRepository) :
        LoginActivityContract.Model {

    override fun getUserIdFromPreferences(): Int {
        return preferencesRepository.getUserId()
    }

    override fun saveUserIdToPreferences(userId: Int) {
        preferencesRepository.saveUserIdToPreferences(userId)
    }

    override fun getUser(username: String): Single<User> {
        val name = username.trim().split(" ")
        return try {
            databaseRepository.getUserByNameAndLastName(name[0], name[1])
        } catch (e: IndexOutOfBoundsException) {
            databaseRepository.getUserByNameAndLastName(name[0], name[0])
        }
    }
}