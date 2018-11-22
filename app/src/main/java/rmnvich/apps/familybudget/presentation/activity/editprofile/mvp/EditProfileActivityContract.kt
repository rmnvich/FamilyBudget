package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import android.content.Intent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface EditProfileActivityContract {

    interface View : MvpView {

        fun setUser(user: User)

        fun setImageView(photoPath: String)

        fun onClickImageView()

        fun onClickApply()
    }

    interface Presenter : MvpPresenter<View> {

        fun setUserId(userId: Int)

        fun onApplyClicked(user: User)

        fun showImageDialog()

        fun onImageViewClicked()

        fun onActivityResult(data: Intent?)

        fun requestPermissions()
    }

    interface Model : MvpModel {

        fun getUserById(userId: Int): Single<User>

        fun updateUserIncomeTypes(user: User): Completable

        fun getFilePath(data: Intent?): Observable<String>

        fun getRealPath(data: Intent?): String
    }
}