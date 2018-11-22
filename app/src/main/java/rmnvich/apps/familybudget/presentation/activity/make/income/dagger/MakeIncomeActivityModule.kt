package rmnvich.apps.familybudget.presentation.activity.make.income.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.make.income.mvp.MakeIncomeActivityModel
import rmnvich.apps.familybudget.presentation.activity.make.income.mvp.MakeIncomeActivityPresenter

@Module
class MakeIncomeActivityModule : BaseModule {

    @PerMakeIncomeActivity
    @Provides
    fun providePresenter(model: MakeIncomeActivityModel,
                         compositeDisposable: CompositeDisposable): MakeIncomeActivityPresenter {
        return MakeIncomeActivityPresenter(model, compositeDisposable)
    }

    @PerMakeIncomeActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerMakeIncomeActivity
    @Provides
    fun provideModel(databaseRepository: DatabaseRepositoryImpl,
                     preferencesRepository: PreferencesRepositoryImpl): MakeIncomeActivityModel {
        return MakeIncomeActivityModel(databaseRepository, preferencesRepository)
    }
}