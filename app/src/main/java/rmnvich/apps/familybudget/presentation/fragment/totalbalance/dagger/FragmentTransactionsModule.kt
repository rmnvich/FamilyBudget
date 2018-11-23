package rmnvich.apps.familybudget.presentation.fragment.totalbalance.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.local.FileRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.adapter.totalbalance.TotalBalanceAdapter
import rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp.FragmentTransactionsModel
import rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp.FragmentTransactionsPresenter

@Module
class FragmentTransactionsModule : BaseModule {

    @PerFragmentTransactions
    @Provides
    fun providePresenter(model: FragmentTransactionsModel,
                         compositeDisposable: CompositeDisposable): FragmentTransactionsPresenter {
        return FragmentTransactionsPresenter(model, compositeDisposable)
    }

    @PerFragmentTransactions
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentTransactions
    @Provides
    fun provideModel(databaseRepository: DatabaseRepositoryImpl,
                     fileRepository: FileRepositoryImpl): FragmentTransactionsModel {
        return FragmentTransactionsModel(databaseRepository, fileRepository)
    }

    @PerFragmentTransactions
    @Provides
    fun provideAdapter(): TotalBalanceAdapter {
        return TotalBalanceAdapter()
    }
}