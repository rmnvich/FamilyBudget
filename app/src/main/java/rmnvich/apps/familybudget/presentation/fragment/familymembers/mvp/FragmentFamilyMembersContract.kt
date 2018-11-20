package rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface FragmentFamilyMembersContract {

    interface View : MvpView {

        fun updateAdapter(data: List<User>)
    }

    interface Presenter : MvpPresenter<View>

    interface Model : MvpModel {

        fun getAllUsers(): Flowable<List<User>>
    }
}