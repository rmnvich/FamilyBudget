package rmnvich.apps.familybudget.presentation.activity.make.income.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.make.income.mvp.MakeIncomeActivity

@PerMakeIncomeActivity
@Subcomponent(modules = [(MakeIncomeActivityModule::class)])
interface MakeIncomeActivityComponent : BaseComponent<MakeIncomeActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<MakeIncomeActivityComponent, MakeIncomeActivityModule>
}