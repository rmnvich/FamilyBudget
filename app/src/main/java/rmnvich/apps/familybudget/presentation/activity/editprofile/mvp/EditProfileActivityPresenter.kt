package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import android.Manifest
import android.app.Activity
import android.content.Intent
import com.tbruyelle.rxpermissions2.RxPermissions
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import java.io.IOException

class EditProfileActivityPresenter(private val model: EditProfileActivityModel,
                                   private val compositeDisposable: CompositeDisposable) :
        PresenterBase<EditProfileActivityContract.View>(), EditProfileActivityContract.Presenter {

    private var mRxPermissions: RxPermissions? = null
    private var userId = -1

    override fun attachView(mvpView: EditProfileActivityContract.View) {
        super.attachView(mvpView)
        mRxPermissions = RxPermissions((view as EditProfileActivity))
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

    override fun setUserId(userId: Int) {
        this.userId = userId
    }

    override fun showImageDialog() {
        val photoPickerIntent = Intent(Intent.ACTION_PICK).setType("image/*")
        (view as Activity).startActivityForResult(Intent.createChooser(photoPickerIntent,
                getString(R.string.select_a_file)), Constants.REQUEST_CODE_PHOTO)
    }

    override fun onImageViewClicked() {
        requestPermissions()
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

    override fun onApplyClicked(user: User) {
        view?.showProgress()
        val disposable = model.updateUserIncomeTypes(user)
                .subscribe({
                    view?.hideProgress()
                    (view as EditProfileActivity).finish()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}
