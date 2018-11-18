package rmnvich.apps.familybudget.presentation.activity.login.mvp

import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.presentation.mvp.MvpModel
import rmnvich.apps.familybudget.presentation.mvp.MvpPresenter
import rmnvich.apps.familybudget.presentation.mvp.MvpView

interface LoginActivityContract {

    interface View : MvpView {

        fun onClickLogin()

        fun onClickRegister()
    }

    interface Presenter : MvpPresenter<View> {

        fun onLoginClicked(username: String, password: String)

        fun onRegisterClicked()

        fun login(userId: Int)

        fun isDataCorrect(username: String, password: String): Boolean

    }

    interface Model : MvpModel {

        fun getUserIdFromPreferences(): Int

        fun saveUserIdToPreferences(userId: Int)

        fun getUser(username: String): Single<User>
    }
}