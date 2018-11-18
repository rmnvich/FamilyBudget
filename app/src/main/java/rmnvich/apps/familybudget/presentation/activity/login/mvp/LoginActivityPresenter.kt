package rmnvich.apps.familybudget.presentation.activity.login.mvp

import android.app.Activity
import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivity
import rmnvich.apps.familybudget.presentation.mvp.PresenterBase

class LoginActivityPresenter(private val model: LoginActivityModel,
                             private val compositeDisposable: CompositeDisposable) :
        PresenterBase<LoginActivityContract.View>(), LoginActivityContract.Presenter {

    override fun viewIsReady() {

    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun onLoginClicked(username: String, password: String) {
        view?.showMessage("$username with password $password")
    }

    override fun onRegisterClicked() {
        (view as LoginActivity).startActivity(Intent((view as Activity),
                RegisterActivity::class.java))
        (view as LoginActivity).overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out)
    }
}