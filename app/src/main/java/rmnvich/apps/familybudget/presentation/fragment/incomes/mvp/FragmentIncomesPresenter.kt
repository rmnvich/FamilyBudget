package rmnvich.apps.familybudget.presentation.fragment.incomes.mvp

import android.content.Intent
import android.os.Handler
import android.support.v4.app.Fragment
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.income.mvp.MakeIncomeActivity
import java.util.*

class FragmentIncomesPresenter(private val model: FragmentIncomesModel,
                               private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentIncomesContract.View>(), FragmentIncomesContract.Presenter {

    private var allIncomesDisposable: Disposable? = null
    private var sortedIncomesDisposable: Disposable? = null

    override fun viewIsReady() {
        getAllIncomes()
    }

    override fun getAllIncomes() {
        if (sortedIncomesDisposable != null && !sortedIncomesDisposable?.isDisposed!!)
            sortedIncomesDisposable?.dispose()

        if (allIncomesDisposable != null && !allIncomesDisposable?.isDisposed!!)
            allIncomesDisposable?.dispose()

        view?.showProgress()
        allIncomesDisposable = model.getAllIncomes()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
    }

    override fun getSortedIncomes(timeRangeStart: Long, timeRangeEnd: Long) {
        if (allIncomesDisposable != null && !allIncomesDisposable?.isDisposed!!)
            allIncomesDisposable?.dispose()

        if (sortedIncomesDisposable != null && !sortedIncomesDisposable?.isDisposed!!)
            sortedIncomesDisposable?.dispose()

        view?.showProgress()
        sortedIncomesDisposable = model.getSortedIncomes(timeRangeStart, timeRangeEnd)
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

        calendar.set(year, monthOfYear, dayOfMonth, 0, 0, 0)
        val timeRangeStart = calendar.timeInMillis
        calendar.set(yearEnd, monthOfYearEnd, dayOfMonthEnd, 23, 59, 59)
        val timeRangeEnd = calendar.timeInMillis

        when {
            year == yearEnd && monthOfYear == monthOfYearEnd && dayOfMonth == dayOfMonthEnd -> getAllIncomes()
            timeRangeStart < timeRangeEnd -> getSortedIncomes(timeRangeStart, timeRangeEnd)
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

    override fun onFabClicked() {
        (view as Fragment).startActivity(Intent((view as Fragment).activity,
                MakeIncomeActivity::class.java))
    }

    override fun onIncomeClicked(id: Int) {
        (view as Fragment).startActivity(Intent((view as Fragment).activity,
                MakeIncomeActivity::class.java)
                .putExtra(Constants.EXTRA_INCOME_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
        allIncomesDisposable?.dispose()
        sortedIncomesDisposable?.dispose()
    }
}