package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import android.content.Intent
import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivity
import rmnvich.apps.familybudget.presentation.mvp.PresenterBase

class DashboardActivityPresenter(private val model: DashboardActivityModel,
                                 private val compositeDisposable: CompositeDisposable) :
        PresenterBase<DashboardActivityContract.View>(), DashboardActivityContract.Presenter {

    private var userId: Int = -1

    override fun setUserId(userId: Int) {
        this.userId = userId
    }

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getUserById(userId)
                .subscribe({
                    view?.hideProgress()
                    view?.setUser(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onLogoutClicked() {
        model.deleteUserFromPreferences()

        Handler().postDelayed({
            (view as DashboardActivity).startActivity(Intent((view as DashboardActivity),
                    LoginActivity::class.java))
            (view as DashboardActivity).finish()
            (view as DashboardActivity).overridePendingTransition(
                    android.R.anim.fade_in, android.R.anim.fade_out)
        }, 250)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}