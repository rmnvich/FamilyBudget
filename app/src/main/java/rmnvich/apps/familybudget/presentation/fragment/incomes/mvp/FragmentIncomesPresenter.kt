package rmnvich.apps.familybudget.presentation.fragment.incomes.mvp

import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class FragmentIncomesPresenter(private val model: FragmentIncomesModel,
                               private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentIncomesContract.View>(), FragmentIncomesContract.Presenter {

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getAllIncomes()
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
//        (view as FragmentIncomes).startActivity(Intent(
//                            (view as FragmentIncomes).activity,
//                            MakeIncomeActivity::class.java))
    }

    override fun onIncomeClicked(id: Int) {
//        (view as FragmentIncomes).startActivity(Intent((view as FragmentIncomes).activity,
//                MakeIncomeActivity::class.java)
//                .putExtra(Constants.EXTRA_INCOME_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}