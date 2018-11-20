package rmnvich.apps.familybudget.presentation.activity.make.expense.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivityModel
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivityPresenter

@Module
class MakeExpenseActivityModule : BaseModule {

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
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): MakeExpenseActivityModel {
        return MakeExpenseActivityModel(databaseRepositoryImpl)
    }
}