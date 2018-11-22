package rmnvich.apps.familybudget.presentation.activity.editprofile.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.data.repository.local.FileRepositoryImpl
import rmnvich.apps.familybudget.domain.di.base.BaseModule
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivityModel
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivityPresenter
import rmnvich.apps.familybudget.presentation.adapter.incometypes.IncomeTypesAdapter

@Module
class EditProfileActivityModule(private val context: Context) : BaseModule {

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
    fun provideModel(databaseRepository: DatabaseRepositoryImpl,
                     fileRepository: FileRepositoryImpl): EditProfileActivityModel {
        return EditProfileActivityModel(databaseRepository, fileRepository)
    }

    @PerEditProfileActivity
    @Provides
    fun provideAdapter(): IncomeTypesAdapter {
        val incomeTypes = context.resources.getStringArray(R.array.income_types)
        return IncomeTypesAdapter(incomeTypes)
    }
}