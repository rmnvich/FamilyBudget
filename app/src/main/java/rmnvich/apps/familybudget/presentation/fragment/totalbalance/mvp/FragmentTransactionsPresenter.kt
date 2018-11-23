package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import android.util.Log
import android.util.Log.d
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.common.Constants.TYPE_EXPENSE
import rmnvich.apps.familybudget.data.common.Constants.TYPE_INCOME
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity
import rmnvich.apps.familybudget.presentation.activity.make.income.mvp.MakeIncomeActivity
import java.util.*

class FragmentTransactionsPresenter(private val model: FragmentTransactionsModel,
                                    private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentTransactionsContract.View>(), FragmentTransactionsContract.Presenter {

    private var allTransactionsDisposable: Disposable? = null
    private var sortedTransactionsDisposable: Disposable? = null

    override fun viewIsReady() {
        getAllTransactions()
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        allTransactionsDisposable?.dispose()
        sortedTransactionsDisposable?.dispose()
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

    override fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int,
                           yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        val calendar = Calendar.getInstance()

        calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0)
        val timeRangeStart = calendar.timeInMillis
        calendar.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 23, 59, 59)
        val timeRangeEnd = calendar.timeInMillis

        when {
            year == yearEnd && monthOfYear == monthOfYearEnd && dayOfMonth == dayOfMonthEnd -> {
                getAllTransactions()
                view?.saveTimeRange(0L, 0L)
            }
            timeRangeStart < timeRangeEnd -> {
                getSortedTransactions(timeRangeStart, timeRangeEnd)
                view?.saveTimeRange(timeRangeStart, timeRangeEnd)
            }
            else -> view?.showMessage(getString(R.string.time_range_error))
        }
    }

    override fun getSortedTransactions(timeRangeStart: Long, timeRangeEnd: Long) {
        if (allTransactionsDisposable != null && !allTransactionsDisposable?.isDisposed!!) {
            allTransactionsDisposable?.dispose()
        }
        if (sortedTransactionsDisposable != null && !sortedTransactionsDisposable?.isDisposed!!) {
            sortedTransactionsDisposable?.dispose()
        }

        view?.showProgress()
        sortedTransactionsDisposable = model.getSortedTransactions(timeRangeStart, timeRangeEnd)
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
    }

    override fun getAllTransactions() {
        if (sortedTransactionsDisposable != null && !sortedTransactionsDisposable?.isDisposed!!)
            sortedTransactionsDisposable?.dispose()

        if (allTransactionsDisposable != null && !allTransactionsDisposable?.isDisposed!!)
            allTransactionsDisposable?.dispose()

        view?.showProgress()
        allTransactionsDisposable = model.getAllTransactions()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
    }

    override fun onItemClicked(transactionId: Int, transactionType: Int) {
        when (transactionType) {
            TYPE_INCOME -> (view as Fragment).startActivityForResult(Intent((view as Fragment).activity,
                    MakeIncomeActivity::class.java)
                    .putExtra(Constants.EXTRA_INCOME_ID, transactionId), 0)
            TYPE_EXPENSE -> (view as Fragment).startActivityForResult(Intent((view as Fragment).activity,
                    MakeExpenseActivity::class.java)
                    .putExtra(Constants.EXTRA_EXPENSE_ID, transactionId), 0)
        }
    }
}