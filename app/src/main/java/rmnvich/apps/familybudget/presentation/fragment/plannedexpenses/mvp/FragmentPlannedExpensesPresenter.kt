package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity
import java.util.*

class FragmentPlannedExpensesPresenter(private val model: FragmentPlannedExpensesModel,
                                       private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentPlannedExpensesContract.View>(), FragmentPlannedExpensesContract.Presenter {

    private var allExpensesDisposable: Disposable? = null
    private var sortedExpensesDisposable: Disposable? = null

    override fun viewIsReady() {
        getAllExpenses()
    }

    override fun getAllExpenses() {
        if (sortedExpensesDisposable != null && !sortedExpensesDisposable?.isDisposed!!) {
            sortedExpensesDisposable?.dispose()
        }

        if (allExpensesDisposable != null && !allExpensesDisposable?.isDisposed!!) {
            allExpensesDisposable?.dispose()
        }

        view?.showProgress()
        allExpensesDisposable = model.getAllPlannedExpenses()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
    }

    override fun getSortedExpenses(timeRangeStart: Long, timeRangeEnd: Long) {
        if (allExpensesDisposable != null && !allExpensesDisposable?.isDisposed!!) {
            allExpensesDisposable?.dispose()
        }

        if (sortedExpensesDisposable != null && !sortedExpensesDisposable?.isDisposed!!) {
            sortedExpensesDisposable?.dispose()
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

    override fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int,
                           yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(year, monthOfYear, dayOfMonth)
        val timeRangeStart = calendar.timeInMillis
        calendar.set(yearEnd, monthOfYearEnd, dayOfMonthEnd)
        val timeRangeEnd = calendar.timeInMillis

        when {
            timeRangeStart < timeRangeEnd -> getSortedExpenses(timeRangeStart, timeRangeEnd)
            timeRangeStart == timeRangeEnd -> getAllExpenses()
            else -> view?.showMessage(getString(R.string.time_range_error))
        }
    }

    override fun onFilterClicked() {
        val calendar = Calendar.getInstance()

        Handler().postDelayed({
            view?.showDatePickerDialog(
                    calendar.get(Calendar.YEAR),
                    calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH))
        }, 100)

    }

    override fun onApplyExpenseClicked(expense: Expense) {
        view?.showProgress()
        val disposable = model.updateExpense(expense)
                .subscribe({
                    view?.hideProgress()
                    (((view as Fragment).activity)
                            as DashboardActivity).updateBalance()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onFabClicked() {
        (view as Fragment).startActivity(Intent((view as Fragment).activity,
                MakeExpenseActivity::class.java))
    }

    override fun onExpenseClicked(id: Int) {
        (view as Fragment).startActivity(Intent((view as Fragment).activity,
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