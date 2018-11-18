package rmnvich.apps.familybudget.presentation.fragment.familymembers.dagger

import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.data.di.base.BaseModule
import rmnvich.apps.familybudget.data.repository.database.DatabaseRepositoryImpl
import rmnvich.apps.familybudget.presentation.adapter.familymembers.FamilyMembersAdapter
import rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp.FragmentFamilyMembersModel
import rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp.FragmentFamilyMembersPresenter

@Module
class FragmentFamilyMembersModule : BaseModule {

    @PerFragmentFamilyMembers
    @Provides
    fun providePresenter(model: FragmentFamilyMembersModel,
                         compositeDisposable: CompositeDisposable): FragmentFamilyMembersPresenter {
        return FragmentFamilyMembersPresenter(model, compositeDisposable)
    }

    @PerFragmentFamilyMembers
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable {
        return CompositeDisposable()
    }

    @PerFragmentFamilyMembers
    @Provides
    fun provideModel(databaseRepositoryImpl: DatabaseRepositoryImpl): FragmentFamilyMembersModel {
        return FragmentFamilyMembersModel(databaseRepositoryImpl)
    }

    @PerFragmentFamilyMembers
    @Provides
    fun provideAdapter(): FamilyMembersAdapter {
        return FamilyMembersAdapter()
    }
}