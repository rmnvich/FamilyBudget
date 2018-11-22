package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class EditProfileActivityPresenter(private val model: EditProfileActivityModel,
                                   private val compositeDisposable: CompositeDisposable) :
        PresenterBase<EditProfileActivityContract.View>(), EditProfileActivityContract.Presenter {

    private var userId = -1

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
