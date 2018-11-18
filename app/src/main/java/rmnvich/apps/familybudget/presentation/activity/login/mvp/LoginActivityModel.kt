package rmnvich.apps.familybudget.presentation.activity.login.mvp

import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl

class LoginActivityModel(private val databaseRepository: DatabaseRepositoryImpl,
                         private val preferencesRepository: PreferencesRepositoryImpl) :
        LoginActivityContract.Model {



}