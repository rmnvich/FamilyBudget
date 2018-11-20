package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import io.reactivex.Completable
import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface FragmentPlannedExpensesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Expense>)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onExpenseClicked(id: Int)

        fun onApplyExpenseClicked(id: Int)
    }

    interface Model : MvpModel {

        fun updateExpense(id: Int): Completable

        fun getAllPlannedExpenses(): Flowable<List<Expense>>
    }
}