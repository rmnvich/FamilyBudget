package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface EditProfileActivityContract {

    interface View : MvpView {

        fun setUser(user: User)

        fun onClickApply()
    }

    interface Presenter : MvpPresenter<View> {

        fun setUserId(userId: Int)

        fun onApplyClicked(user: User)
    }

    interface Model : MvpModel {

        fun getUserById(userId: Int): Single<User>

        fun updateUserIncomeTypes(user: User): Completable
    }
}