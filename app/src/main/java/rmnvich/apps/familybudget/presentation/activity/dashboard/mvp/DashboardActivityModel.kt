package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl

class DashboardActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                             private val preferencesRepository: PreferencesRepositoryImpl) :
        DashboardActivityContract.Model {

    override fun getUserById(userId: Int): Single<User> {
        return databaseRepository.getUserById(userId)
    }

    override fun deleteUserFromPreferences() {
        preferencesRepository.deleteUserIdFromPreferences()
    }
}