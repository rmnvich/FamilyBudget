package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentActualExpensesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Expense>)

        fun showDatePickerDialog(year: Int, month: Int, day: Int)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onFilterClicked()

        fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int,
                      yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int)

        fun getAllExpenses()

        fun getSortedExpenses(timeRangeStart: Long, timeRangeEnd: Long)

        fun onExpenseClicked(id: Int)
    }

    interface Model : MvpModel {

        fun getBalance(): Single<Balance>

        fun getSortedExpenses(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Expense>>

        fun getAllActualExpenses(): Flowable<List<Expense>>
    }
}