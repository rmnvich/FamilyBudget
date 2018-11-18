package rmnvich.apps.familybudget.presentation.activity.register.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.data.di.base.BaseComponent
import rmnvich.apps.familybudget.data.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivity

@PerRegisterActivity
@Subcomponent(modules = [(RegisterActivityModule::class)])
interface RegisterActivityComponent : BaseComponent<RegisterActivity> {

    @Subcomponent.Builder
    interface Builder: BaseComponentBuilder<RegisterActivityComponent, RegisterActivityModule>
}