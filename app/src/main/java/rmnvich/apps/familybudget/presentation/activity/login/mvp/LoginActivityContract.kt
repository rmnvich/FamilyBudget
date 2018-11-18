package rmnvich.apps.familybudget.presentation.activity.login.mvp

import rmnvich.apps.familybudget.presentation.mvp.MvpModel
import rmnvich.apps.familybudget.presentation.mvp.MvpPresenter
import rmnvich.apps.familybudget.presentation.mvp.MvpView

interface LoginActivityContract {

    interface View: MvpView {

        fun onClickLogin()

        fun onClickRegister()
    }

    interface Presenter: MvpPresenter<View> {

        fun onLoginClicked(username: String, password: String)

        fun onRegisterClicked()

    }

    interface Model: MvpModel {

    }
}