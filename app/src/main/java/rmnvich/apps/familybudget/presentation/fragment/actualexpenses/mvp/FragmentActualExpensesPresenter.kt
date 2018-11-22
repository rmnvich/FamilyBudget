package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity
import rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp.FragmentPlannedExpenses

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
        view?.showProgress()
        val disposable = model.getBalance()
                .subscribe({
                    view?.hideProgress()
                    if (it.balance.toDouble() < 0) {
                        view?.showMessage(getString(R.string.negative_balance))
                    } else (view as FragmentActualExpenses).startActivity(Intent(
                            (view as FragmentActualExpenses).activity,
                            MakeExpenseActivity::class.java))
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)

    }

    override fun onExpenseClicked(id: Int) {
        (view as FragmentActualExpenses).startActivity(Intent((view as FragmentActualExpenses).activity,
                MakeExpenseActivity::class.java)
                .putExtra(Constants.EXTRA_EXPENSE_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}