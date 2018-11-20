package rmnvich.apps.familybudget.presentation.activity.dashboard.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity

@PerDashboardActivity
@Subcomponent(modules = [(DashboardActivityModule::class)])
interface DashboardActivityComponent : BaseComponent<DashboardActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<DashboardActivityComponent, DashboardActivityModule>
}