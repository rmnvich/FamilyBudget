package rmnvich.apps.familybudget.presentation.activity.editprofile.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.preferences.PreferencesRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivityModel
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivityPresenter

@Module
class EditProfileActivityModule : BaseModule {

    @PerEditProfileActivity
    @Provides
    fun providePresenter(model: EditProfileActivityModel,
                         compositeDisposable: CompositeDisposable): EditProfileActivityPresenter {
        return EditProfileActivityPresenter(model, compositeDisposable)
    }

    @PerEditProfileActivity
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerEditProfileActivity
    @Provides
    fun provideModel(databaseRepository: DatabaseRepositoryImpl): EditProfileActivityModel {
        return EditProfileActivityModel(databaseRepository)
    }
}