package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class FragmentTransactionsPresenter(private val model: FragmentTransactionsModel,
                                    private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentTransactionsContract.View>(), FragmentTransactionsContract.Presenter {

    override fun viewIsReady() {
        getAllTransactions()
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun getAllTransactions() {
        view?.showProgress()
        val disposable = model.getAllTransactions()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }
}