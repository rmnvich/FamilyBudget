package rmnvich.apps.familybudget.presentation.fragment.totalbalance.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp.FragmentTransactions

@PerFragmentTransactions
@Subcomponent(modules = [(FragmentTransactionsModule::class)])
interface FragmentTransactionsComponent : BaseComponent<FragmentTransactions> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentTransactionsComponent, FragmentTransactionsModule>
}