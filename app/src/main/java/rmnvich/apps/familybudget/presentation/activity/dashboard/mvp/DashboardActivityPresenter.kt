package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import android.content.Intent
import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.data.common.Constants.REQUEST_CODE_PHOTO
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.presentation.activity.login.mvp.LoginActivity
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.editprofile.mvp.EditProfileActivity


class DashboardActivityPresenter(private val model: DashboardActivityModel,
                                 private val compositeDisposable: CompositeDisposable) :
        PresenterBase<DashboardActivityContract.View>(), DashboardActivityContract.Presenter {

    private var userId: Int = -1

    override fun setUserId(userId: Int) {
        this.userId = userId
    }

    override fun viewIsReady() {
        getUserById(userId)
        updateBalance()
    }

    override fun getUserById(userId: Int) {
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

    override fun updateBalance() {
        view?.showProgress()
        val disposable = model.getBalance()
                .subscribe({
                    view?.hideProgress()
                    view?.setBalance(it)
                }, {
                    view?.hideProgress()
                    if (it.message!!.contains(getString(R.string.returned_empty))) {
                        view?.showInitialBalanceDialog()
                    } else view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onApplyBalanceDialogClicked(value: String) {
        view?.showProgress()
        val balance = Balance(value)
        val disposable = model.insertBalance(balance)
                .subscribe({
                    view?.hideProgress()
                    view?.setBalance(balance)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onEditProfileClicked() {
        Handler().postDelayed({
            (view as DashboardActivity).startActivityForResult(Intent((view as DashboardActivity),
                    EditProfileActivity::class.java).putExtra(EXTRA_USER_ID, userId),
                    REQUEST_CODE_PHOTO)
        }, 250)
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