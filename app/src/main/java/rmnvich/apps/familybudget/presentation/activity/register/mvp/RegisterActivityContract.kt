package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.content.Intent
import io.reactivex.Completable
import io.reactivex.Observable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface RegisterActivityContract {

    interface View : MvpView {

        fun setUser(user: User)

        fun setImageView(photoPath: String)

        fun onClickImageView()

        fun onClickRegister()
    }

    interface Presenter : MvpPresenter<View> {

        fun showImageDialog()

        fun onImageViewClicked()

        fun onRegisterClicked(user: User)

        fun onActivityResult(data: Intent?)

        fun requestPermissions()

        fun isDataCorrect(user: User): Boolean
    }

    interface Model : MvpModel {

        fun checkIfExists(user: User): Single<User>

        fun insertUser(user: User): Completable

        fun getFilePath(data: Intent?): Observable<String>

        fun getRealPath(data: Intent?): String
    }
}