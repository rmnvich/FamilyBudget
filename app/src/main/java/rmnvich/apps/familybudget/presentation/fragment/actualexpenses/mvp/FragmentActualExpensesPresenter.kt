package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.domain.interactor.mvp.PresenterBase

class FragmentActualExpensesPresenter(private val model: FragmentActualExpensesModel,
                                      private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentActualExpensesContract.View>(), FragmentActualExpensesContract.Presenter {

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getAllActualExpenses()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onFabClicked() {
//        (view as FragmentActualExpenses).startActivity(Intent((view as FragmentActualExpenses).activity,
//                MakeCategoryActivity::class.java))
    }

    override fun onExpenseClicked(id: Int) {
//        (view as FragmentCategories).startActivity(Intent((view as FragmentCategories).activity,
//                MakeCategoryActivity::class.java)
//                .putExtra(Constants.EXTRA_CATEGORY_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}