package rmnvich.apps.familybudget.presentation.fragment.categories.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.categories.mvp.FragmentCategories
import rmnvich.apps.familybudget.presentation.fragment.familymembers.dagger.PerFragmentCategories

@PerFragmentCategories
@Subcomponent(modules = [(FragmentCategoriesModule::class)])
interface FragmentCategoriesComponent : BaseComponent<FragmentCategories> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentCategoriesComponent, FragmentCategoriesModule>
}