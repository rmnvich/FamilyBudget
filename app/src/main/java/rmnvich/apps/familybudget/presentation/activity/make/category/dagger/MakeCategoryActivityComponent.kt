package rmnvich.apps.familybudget.presentation.activity.make.category.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.make.category.mvp.MakeCategoryActivity

@PerMakeCategoryActivity
@Subcomponent(modules = [(MakeCategoryActivityModule::class)])
interface MakeCategoryActivityComponent : BaseComponent<MakeCategoryActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<MakeCategoryActivityComponent, MakeCategoryActivityModule>
}