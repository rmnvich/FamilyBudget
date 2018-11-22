package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentPlannedExpensesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Expense>)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onExpenseClicked(id: Int)

        fun onApplyExpenseClicked(expense: Expense)
    }

    interface Model : MvpModel {

        fun updateExpense(expense: Expense): Completable

        fun getAllPlannedExpenses(): Flowable<List<Expense>>
    }
}