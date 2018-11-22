package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import android.content.Intent
import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity
import rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp.FragmentPlannedExpenses

class FragmentActualExpensesPresenter(private val model: FragmentActualExpensesModel,
                                      private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentActualExpensesContract.View>(), FragmentActualExpensesContract.Presenter {

    private var allExpensesDisposable: Disposable? = null
    private var sortedExpensesDisposable: Disposable? = null

    override fun viewIsReady() {
        view?.showProgress()
        allExpensesDisposable = model.getAllActualExpenses()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
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

    override fun onFilterClicked() {
        Handler().postDelayed({
            view?.showDatePickerDialog()
        }, 250)
    }

    override fun getSortedExpenses(timeRangeStart: Long, timeRangeEnd: Long) {
        if (allExpensesDisposable != null && !allExpensesDisposable?.isDisposed!!) {
            allExpensesDisposable?.dispose()
        }

        view?.showProgress()
        sortedExpensesDisposable = model.getSortedExpenses(timeRangeStart, timeRangeEnd)
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
    }

    override fun onExpenseClicked(id: Int) {
        (view as FragmentActualExpenses).startActivity(Intent((view as FragmentActualExpenses).activity,
                MakeExpenseActivity::class.java)
                .putExtra(Constants.EXTRA_EXPENSE_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        allExpensesDisposable?.dispose()
        sortedExpensesDisposable?.dispose()
    }
}