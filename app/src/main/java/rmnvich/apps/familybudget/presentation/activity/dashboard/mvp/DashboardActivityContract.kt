package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.presentation.mvp.MvpModel
import rmnvich.apps.familybudget.presentation.mvp.MvpPresenter
import rmnvich.apps.familybudget.presentation.mvp.MvpView

interface DashboardActivityContract {

    interface View : MvpView {

        fun setUser(user: User)
    }

    interface Presenter : MvpPresenter<View> {

        fun setUserId(userId: Int)

        fun onLogoutClicked()
    }

    interface Model : MvpModel {

        fun getUserById(userId: Int): Single<User>

        fun deleteUserFromPreferences()
    }
}