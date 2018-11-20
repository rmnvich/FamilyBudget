package rmnvich.apps.familybudget.presentation.activity.make.expense.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivityModel
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivityPresenter
import rmnvich.apps.familybudget.presentation.dialog.DialogCategories

@Module
class MakeExpenseActivityModule(private val context: Context) : BaseModule {

    @PerMakeExpenseActivity
    @Provides
    fun providePresenter(model: MakeExpenseActivityModel,
                         compositeDisposable: CompositeDisposable): MakeExpenseActivityPresenter {
        return MakeExpenseActivityPresenter(model, compositeDisposable)
    }

    @PerMakeExpenseActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerMakeExpenseActivity
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl,
                     preferencesRepositoryImpl: PreferencesRepositoryImpl): MakeExpenseActivityModel {
        return MakeExpenseActivityModel(databaseRepositoryImpl, preferencesRepositoryImpl)
    }

    @Provides
    fun provideCategoryDialog(databaseRepositoryImpl: DatabaseRepositoryImpl): DialogCategories {
        return DialogCategories(context, databaseRepositoryImpl)
    }
}