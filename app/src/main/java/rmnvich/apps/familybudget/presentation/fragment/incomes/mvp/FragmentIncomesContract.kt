package rmnvich.apps.familybudget.presentation.fragment.incomes.mvp

import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentIncomesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Income>)

        fun showDatePickerDialog(year: Int, month: Int, day: Int)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onFilterClicked()

        fun onDateSet(year: Int, monthOfYear: Int, dayOfMonth: Int,
                      yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int)

        fun getAllIncomes()

        fun getSortedIncomes(timeRangeStart: Long, timeRangeEnd: Long)

        fun onIncomeClicked(id: Int)
    }

    interface Model : MvpModel {

        fun getSortedIncomes(timeRangeStart: Long, timeRangeEnd: Long): Flowable<List<Income>>

        fun getAllIncomes(): Flowable<List<Income>>
    }
}