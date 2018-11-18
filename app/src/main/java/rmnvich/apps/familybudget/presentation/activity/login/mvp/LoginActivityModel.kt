package rmnvich.apps.familybudget.presentation.activity.login.mvp

import android.arch.persistence.room.Index
import io.reactivex.Single
import rmnvich.apps.familybudget.R.string.name
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl
import java.lang.IndexOutOfBoundsException

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
        val name = username.split(" ")
        return try {
            databaseRepository.getUserByNameAndLastname(name[0], name[1])
        } catch (e: IndexOutOfBoundsException) {
            databaseRepository.getUserByNameAndLastname(name[0], name[0])
        }
    }
}