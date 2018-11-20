package rmnvich.apps.familybudget.presentation.fragment.categories.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.presentation.adapter.categories.CategoriesAdapter
import rmnvich.apps.familybudget.presentation.fragment.categories.mvp.FragmentCategoriesModel
import rmnvich.apps.familybudget.presentation.fragment.categories.mvp.FragmentCategoriesPresenter

@Module
class FragmentCategoriesModule : BaseModule {

    @PerFragmentCategories
    @Provides
    fun providePresenter(model: FragmentCategoriesModel,
                         compositeDisposable: CompositeDisposable): FragmentCategoriesPresenter {
        return FragmentCategoriesPresenter(model, compositeDisposable)
    }

    @PerFragmentCategories
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentCategories
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): FragmentCategoriesModel {
        return FragmentCategoriesModel(databaseRepositoryImpl)
    }

    @PerFragmentCategories
    @Provides
    fun provideAdapter(): CategoriesAdapter {
        return CategoriesAdapter()
    }
}