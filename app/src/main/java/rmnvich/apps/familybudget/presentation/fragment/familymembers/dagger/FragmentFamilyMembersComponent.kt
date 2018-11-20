package rmnvich.apps.familybudget.presentation.fragment.familymembers.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp.FragmentFamilyMembers

@PerFragmentFamilyMembers
@Subcomponent(modules = [(FragmentFamilyMembersModule::class)])
interface FragmentFamilyMembersComponent : BaseComponent<FragmentFamilyMembers> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<FragmentFamilyMembersComponent, FragmentFamilyMembersModule>
}