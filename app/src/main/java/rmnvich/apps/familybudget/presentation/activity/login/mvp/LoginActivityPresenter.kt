package rmnvich.apps.familybudget.presentation.activity.login.mvp

import android.app.Activity
import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.activity.register.mvp.RegisterActivity
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class LoginActivityPresenter(private val model: LoginActivityModel,
                             private val compositeDisposable: CompositeDisposable) :
        PresenterBase<LoginActivityContract.View>(), LoginActivityContract.Presenter {

    override fun viewIsReady() {
        val userId: Int = model.getUserIdFromPreferences()
        if (userId != -1)
            login(userId)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun onLoginClicked(username: String, password: String) {
        if (isDataCorrect(username, password)) {
            view?.showProgress()
            val disposable = model.getUser(username)
                    .subscribe({
                        view?.hideProgress()
                        if (it.password == password) {
                            model.saveUserIdToPreferences(it.userId)
                            login(it.userId)
                        } else view?.showMessage(getString(R.string.wrong_data))
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.user_not_exists))
                    })
            compositeDisposable.add(disposable)
        } else view?.showMessage(getString(R.string.empty_field_error))
    }

    override fun isDataCorrect(username: String, password: String): Boolean {
        return !(username.isEmpty() ||
                password.isEmpty())
    }

    override fun login(userId: Int) {
        (view as LoginActivity).startActivity(Intent((view as LoginActivity),
                DashboardActivity::class.java).putExtra(EXTRA_USER_ID, userId))
        (view as LoginActivity).finish()
    }

    override fun onRegisterClicked() {
        (view as LoginActivity).startActivity(Intent((view as Activity),
                RegisterActivity::class.java))
        (view as LoginActivity).overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out)
    }
}