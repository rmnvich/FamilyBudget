package rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp

import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.domain.interactor.mvp.PresenterBase

class FragmentFamilyMembersPresenter(private val model: FragmentFamilyMembersModel,
                                     private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentFamilyMembersContract.View>(), FragmentFamilyMembersContract.Presenter {

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getAllUsers()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
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