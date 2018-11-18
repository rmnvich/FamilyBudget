package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.content.Intent
import io.reactivex.Observable
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.presentation.mvp.MvpModel
import rmnvich.apps.familybudget.presentation.mvp.MvpPresenter
import rmnvich.apps.familybudget.presentation.mvp.MvpView

class RegisterActivityContract {

    interface View: MvpView {

        fun setUser(user: User)

        fun setImageView(photoPath: String)

        fun onClickImageView()

        fun onClickRegister()
    }

    interface Presenter: MvpPresenter<View> {

        fun showImageDialog()

        fun onImageViewClicked()

        fun onRegisterClicked(user: User)

        fun onActivityResult(data: Intent?)

        fun requestPermissions()

        fun isDataCorrect(user: User): Boolean
    }

    interface Model: MvpModel {

        fun getFilePath(data: Intent?): Observable<String>

        fun getRealPath(data: Intent?): String
    }
}