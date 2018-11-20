package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp.FragmentPlannedExpenses

@PerFragmentPlannedExpenses
@Subcomponent(modules = [(FragmentPlannedExpensesModule::class)])
interface FragmentPlannedExpensesComponent : BaseComponent<FragmentPlannedExpenses> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentPlannedExpensesComponent, FragmentPlannedExpensesModule>
}