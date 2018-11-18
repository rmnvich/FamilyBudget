package rmnvich.apps.familybudget.presentation.activity.register.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.local.LocalRepositoryImpl
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivityModel
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivityPresenter

@Module
class RegisterActivityModule: BaseModule {

    @PerRegisterActivity
    @Provides
    fun providePresenter(model: RegisterActivityModel,
                         compositeDisposable: CompositeDisposable) : RegisterActivityPresenter {
        return RegisterActivityPresenter(model, compositeDisposable)
    }

    @PerRegisterActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerRegisterActivity
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl,
                     localRepositoryImpl: LocalRepositoryImpl) : RegisterActivityModel {
        return RegisterActivityModel(databaseRepositoryImpl, localRepositoryImpl)
    }
}