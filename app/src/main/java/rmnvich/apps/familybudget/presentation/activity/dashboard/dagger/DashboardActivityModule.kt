package rmnvich.apps.familybudget.presentation.activity.dashboard.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivityModel
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivityPresenter

@Module
class DashboardActivityModule : BaseModule {

    @PerDashboardActivity
    @Provides
    fun providePresenter(model: DashboardActivityModel,
                         compositeDisposable: CompositeDisposable): DashboardActivityPresenter {
        return DashboardActivityPresenter(model, compositeDisposable)
    }

    @PerDashboardActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerDashboardActivity
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl,
                     preferencesRepositoryImpl: PreferencesRepositoryImpl): DashboardActivityModel {
        return DashboardActivityModel(databaseRepositoryImpl, preferencesRepositoryImpl)
    }
}