package rmnvich.apps.familybudget.presentation.activity.register.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivity

@PerRegisterActivity
@Subcomponent(modules = [(RegisterActivityModule::class)])
interface RegisterActivityComponent : BaseComponent<RegisterActivity> {

    @Subcomponent.Builder
    interface Builder: BaseComponentBuilder<RegisterActivityComponent, RegisterActivityModule>
}