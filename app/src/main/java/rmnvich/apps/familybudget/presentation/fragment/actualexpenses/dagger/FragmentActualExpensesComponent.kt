package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp.FragmentActualExpenses

@PerFragmentActualExpenses
@Subcomponent(modules = [(FragmentActualExpensesModule::class)])
interface FragmentActualExpensesComponent : BaseComponent<FragmentActualExpenses> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentActualExpensesComponent, FragmentActualExpensesModule>
}