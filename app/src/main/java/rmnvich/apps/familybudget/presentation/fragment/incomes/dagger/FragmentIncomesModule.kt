package rmnvich.apps.familybudget.presentation.fragment.incomes.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.adapter.incomes.IncomesAdapter
import rmnvich.apps.familybudget.presentation.fragment.incomes.mvp.FragmentIncomesModel
import rmnvich.apps.familybudget.presentation.fragment.incomes.mvp.FragmentIncomesPresenter

@Module
class FragmentIncomesModule : BaseModule {

    @PerFragmentIncomes
    @Provides
    fun providePresenter(model: FragmentIncomesModel,
                         compositeDisposable: CompositeDisposable): FragmentIncomesPresenter {
        return FragmentIncomesPresenter(model, compositeDisposable)
    }

    @PerFragmentIncomes
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentIncomes
    @Provides
    fun provideModel(databaseRepository: DatabaseRepositoryImpl): FragmentIncomesModel {
        return FragmentIncomesModel(databaseRepository)
    }

    @PerFragmentIncomes
    @Provides
    fun provideAdapter(): IncomesAdapter {
        return IncomesAdapter()
    }
}