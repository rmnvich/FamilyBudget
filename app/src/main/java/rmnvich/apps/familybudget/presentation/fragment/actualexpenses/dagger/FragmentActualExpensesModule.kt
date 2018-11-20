package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.adapter.expenses.ActualExpensesAdapter
import rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp.FragmentActualExpensesModel
import rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp.FragmentActualExpensesPresenter

@Module
class FragmentActualExpensesModule : BaseModule {

    @PerFragmentActualExpenses
    @Provides
    fun providePresenter(model: FragmentActualExpensesModel,
                         compositeDisposable: CompositeDisposable): FragmentActualExpensesPresenter {
        return FragmentActualExpensesPresenter(model, compositeDisposable)
    }

    @PerFragmentActualExpenses
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentActualExpenses
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): FragmentActualExpensesModel {
        return FragmentActualExpensesModel(databaseRepositoryImpl)
    }

    @PerFragmentActualExpenses
    @Provides
    fun provideAdapter(): ActualExpensesAdapter {
        return ActualExpensesAdapter()
    }
}