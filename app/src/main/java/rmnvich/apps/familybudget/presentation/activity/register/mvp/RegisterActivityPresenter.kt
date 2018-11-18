package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.util.Log
import android.util.Log.d
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants.REQUEST_CODE_PHOTO
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.presentation.mvp.PresenterBase
import java.io.IOException

class RegisterActivityPresenter(private val model: RegisterActivityModel,
                                private val compositeDisposable: CompositeDisposable) :
        PresenterBase<RegisterActivityContract.View>(), RegisterActivityContract.Presenter {

    private var mRxPermissions: RxPermissions? = null

    override fun attachView(mvpView: RegisterActivityContract.View) {
        super.attachView(mvpView)
        mRxPermissions = RxPermissions((view as RegisterActivity))
    }

    override fun viewIsReady() {
        view?.setUser(User())
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun showImageDialog() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK).setType("image/*")
        (view as Activity).startActivityForResult(Intent.createChooser(photoPickerIntent,
                getString(R.string.select_a_file)), REQUEST_CODE_PHOTO)
    }

    override fun onImageViewClicked() {
        requestPermissions()
    }

    override fun onRegisterClicked(user: User) {
        if (isDataCorrect(user)) {
            view?.showProgress()
            val userExistsDisposable = model.checkIfExists(user)
                    .subscribe({
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.user_exists))
                    }, {
                        if (it.message!!.contains(getString(R.string.returned_empty))) {
                            val insertDisposable = model.insertUser(user)
                                    .subscribe({
                                        view?.hideProgress()
                                        (view as RegisterActivity).finish()
                                    }, {
                                        view?.hideProgress()
                                        view?.showMessage(getString(R.string.error))
                                    })
                            compositeDisposable.add(insertDisposable)
                        }
                    })
            compositeDisposable.add(userExistsDisposable)
        } else view?.showMessage(getString(R.string.empty_field_error))
    }

    override fun isDataCorrect(user: User): Boolean {
        return !(user.name.isEmpty() ||
                user.lastname.isEmpty() ||
                user.relationship.isEmpty() ||
                user.password.isEmpty())
    }

    override fun onActivityResult(data: Intent?) {
        try {
            view?.showProgress()
            val disposable = model.getFilePath(data)
                    .subscribe {
                        view?.setImageView(it)
                        view?.hideProgress()
                    }
            compositeDisposable.add(disposable)
        } catch (e: IOException) {
            view?.setImageView("")
            view?.showMessage(getString(R.string.error))
        }
    }

    override fun requestPermissions() {
        val disposable = mRxPermissions?.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                ?.subscribe { permission ->
                    if (permission) {
                        showImageDialog()
                    } else view?.showMessage(getString(R.string.error))
                }
        compositeDisposable.add(disposable!!)

    }
}