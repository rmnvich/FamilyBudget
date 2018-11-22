package rmnvich.apps.familybudget.presentation.fragment.incomes.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.incomes.mvp.FragmentIncomes

@PerFragmentIncomes
@Subcomponent(modules = [(FragmentIncomesModule::class)])
interface FragmentIncomesComponent : BaseComponent<FragmentIncomes> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentIncomesComponent, FragmentIncomesModule>
}