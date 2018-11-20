package rmnvich.apps.familybudget.presentation.fragment.actualexpenses.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface FragmentActualExpensesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Expense>)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onExpenseClicked(id: Int)
    }

    interface Model : MvpModel {

        fun getAllActualExpenses(): Flowable<List<Expense>>
    }
}