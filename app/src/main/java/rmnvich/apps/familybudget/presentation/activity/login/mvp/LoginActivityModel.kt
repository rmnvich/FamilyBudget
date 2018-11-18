package rmnvich.apps.familybudget.presentation.activity.login.mvp

import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl

class LoginActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                         private val preferencesRepository: PreferencesRepositoryImpl) :
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
            databaseRepository.getUserByNameAndLastname(name[0], name[1])
        } catch (e: IndexOutOfBoundsException) {
            databaseRepository.getUserByNameAndLastname(name[0], name[0])
        }
    }
}