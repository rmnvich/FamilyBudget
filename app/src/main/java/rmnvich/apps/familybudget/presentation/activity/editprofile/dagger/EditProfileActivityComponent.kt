package rmnvich.apps.familybudget.presentation.activity.editprofile.dagger

import dagger.Subcomponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponent
import rmnvich.apps.familybudget.domain.di.base.BaseComponentBuilder
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivity

@PerEditProfileActivity
@Subcomponent(modules = [(EditProfileActivityModule::class)])
interface EditProfileActivityComponent : BaseComponent<EditProfileActivity> {

    @Subcomponent.Builder
    interface Builder : BaseComponentBuilder<EditProfileActivityComponent, EditProfileActivityModule>
}