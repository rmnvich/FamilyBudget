package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.adapter.expenses.PlannedExpensesAdapter
import rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp.FragmentPlannedExpensesModel
import rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp.FragmentPlannedExpensesPresenter

@Module
class FragmentPlannedExpensesModule : BaseModule {

    @PerFragmentPlannedExpenses
    @Provides
    fun providePresenter(model: FragmentPlannedExpensesModel,
                         compositeDisposable: CompositeDisposable): FragmentPlannedExpensesPresenter {
        return FragmentPlannedExpensesPresenter(model, compositeDisposable)
    }

    @PerFragmentPlannedExpenses
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentPlannedExpenses
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): FragmentPlannedExpensesModel {
        return FragmentPlannedExpensesModel(databaseRepositoryImpl)
    }

    @PerFragmentPlannedExpenses
    @Provides
    fun provideAdapter(): PlannedExpensesAdapter {
        return PlannedExpensesAdapter()
    }
}