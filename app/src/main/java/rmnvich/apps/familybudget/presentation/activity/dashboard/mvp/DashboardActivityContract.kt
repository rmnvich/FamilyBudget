package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import android.support.v4.app.Fragment
import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.presentation.mvp.MvpModel
import rmnvich.apps.familybudget.presentation.mvp.MvpPresenter
import rmnvich.apps.familybudget.presentation.mvp.MvpView

interface DashboardActivityContract {

    interface View : MvpView {

        fun setUser(user: User)

        fun setBalance(balance: Balance)

        fun updateBalance()

        fun showInitialBalanceDialog()

        fun showFragment(fragment: Fragment)
    }

    interface Presenter : MvpPresenter<View> {

        fun setUserId(userId: Int)

        fun getUserById(userId: Int)

        fun updateBalance()

        fun onApplyBalanceDialogClicked(value: String)

        fun onEditProfileClicked()

        fun onLogoutClicked()
    }

    interface Model : MvpModel {

        fun getUserById(userId: Int): Single<User>

        fun getBalance(): Single<Balance>

        fun insertBalance(balance: Balance): Completable

        fun deleteUserFromPreferences()
    }
}