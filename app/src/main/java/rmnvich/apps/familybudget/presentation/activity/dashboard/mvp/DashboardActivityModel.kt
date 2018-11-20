package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl

class DashboardActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                             private val preferencesRepository: PreferencesRepositoryImpl) :
        DashboardActivityContract.Model {

    override fun getUserById(userId: Int): Single<User> {
        return databaseRepository.getUserById(userId)
    }

    override fun getBalance(): Single<Balance> {
        return databaseRepository.getBalance()
    }

    override fun insertBalance(balance: Balance): Completable {
        return databaseRepository.insertBalance(balance)
    }

    override fun deleteUserFromPreferences() {
        preferencesRepository.deleteUserIdFromPreferences()
    }
}