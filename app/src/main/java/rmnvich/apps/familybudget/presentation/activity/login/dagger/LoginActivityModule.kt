package rmnvich.apps.familybudget.presentation.activity.login.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivityModel
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivityPresenter

@Module
class LoginActivityModule : BaseModule {

    @PerLoginActivity
    @Provides
    fun providePresenter(model: LoginActivityModel,
                         compositeDisposable: CompositeDisposable): LoginActivityPresenter {
        return LoginActivityPresenter(model, compositeDisposable)
    }

    @PerLoginActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerLoginActivity
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl,
                     preferencesRepositoryImpl: PreferencesRepositoryImpl): LoginActivityModel {
        return LoginActivityModel(databaseRepositoryImpl, preferencesRepositoryImpl)
    }

}