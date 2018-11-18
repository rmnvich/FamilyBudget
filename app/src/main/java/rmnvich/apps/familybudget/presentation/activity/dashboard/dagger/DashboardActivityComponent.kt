package rmnvich.apps.familybudget.presentation.activity.dashboard.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.data.di.base.BaseComponent
import rmnvich.apps.familybudget.data.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity

@PerDashboardActivity
@Subcomponent(modules = [(DashboardActivityModule::class)])
interface DashboardActivityComponent : BaseComponent<DashboardActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<DashboardActivityComponent, DashboardActivityModule>
}