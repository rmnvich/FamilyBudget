package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentTransactionsContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Any>)

        fun saveTimeRange(timeRangeStart: Long, timeRangeEnd: Long)

        fun showDatePickerDialog(year: Int, month: Int, day: Int)
    }

    interface Presenter : MvpPresenter<View> {

        fun onFilterClicked()

        fun onExportClicked(timeRangeStart: Long, timeRangeEnd: Long)

        fun onDateSet(
            year: Int, monthOfYear: Int, dayOfMonth: Int,
            yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int
        )

        fun getAllTransactions()

        fun getSortedTransactions(timeRangeStart: Long, timeRangeEnd: Long)

        fun onItemClicked(transactionId: Int, transactionType: Int)
    }

    interface Model : MvpModel {

        fun saveTransactionsToExcel(timeRangeStart: Long, timeRangeEnd: Long): Completable

        fun getAllTransactions(): Flowable<List<Any>>

        fun getSortedTransactions(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Any>>
    }
}