package rmnvich.apps.familybudget.presentation.activity.make.category.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.make.category.mvp.MakeCategoryActivityModel
import rmnvich.apps.familybudget.presentation.activity.make.category.mvp.MakeCategoryActivityPresenter

@Module
class MakeCategoryActivityModule : BaseModule {

    @PerMakeCategoryActivity
    @Provides
    fun providePresenter(model: MakeCategoryActivityModel,
                         compositeDisposable: CompositeDisposable): MakeCategoryActivityPresenter {
        return MakeCategoryActivityPresenter(model, compositeDisposable)
    }

    @PerMakeCategoryActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerMakeCategoryActivity
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): MakeCategoryActivityModel {
        return MakeCategoryActivityModel(databaseRepositoryImpl)
    }
}