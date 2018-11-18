package rmnvich.apps.familybudget.presentation.activity.login.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.data.di.base.BaseComponent
import rmnvich.apps.familybudget.data.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivity

@PerLoginActivity
@Subcomponent(modules = [(LoginActivityModule::class)])
interface LoginActivityComponent : BaseComponent<LoginActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<LoginActivityComponent, LoginActivityModule>
}