package rmnvich.apps.familybudget.presentation.activity.dashboard.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preference.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivityModel
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivityPresenter
import rmnvich.apps.familybudget.presentation.dialog.InitBalanceDialog

@Module
class DashboardActivityModule(private val context: Context) : BaseModule {

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

    @PerDashboardActivity
    @Provides
    fun provideInitBalanceDialog(): InitBalanceDialog {
        return InitBalanceDialog(context)
    }
}