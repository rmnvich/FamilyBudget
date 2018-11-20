package rmnvich.apps.familybudget.presentation.activity.make.expense.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity

@PerMakeExpenseActivity
@Subcomponent(modules = [(MakeExpenseActivityModule::class)])
interface MakeExpenseActivityComponent : BaseComponent<MakeExpenseActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<MakeExpenseActivityComponent, MakeExpenseActivityModule>
}