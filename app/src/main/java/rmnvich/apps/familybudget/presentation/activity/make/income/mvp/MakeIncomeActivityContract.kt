package rmnvich.apps.familybudget.presentation.activity.make.income.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface MakeIncomeActivityContract {

    interface View : MvpView {

        fun setIncome(income: Income)

        fun setSpinnerAdapter(incomeTypes: List<String>, selectedItemPosition: Int)

        fun showConfirmDialog()

        fun onClickApply()
    }

    interface Presenter : MvpPresenter<View> {

        fun setIncomeId(id: Int)

        fun getIncomeById()

        fun getUserById()

        fun onFabClicked(income: Income)

        fun onDialogConfirm()

        fun onClickDelete()

        fun isDataCorrect(income: Income): Boolean
    }

    interface Model : MvpModel {

        fun getIncomeById(id: Int): Single<Income>

        fun getUserById(): Single<User>

        fun insertIncome(income: Income, oldValue: String): Completable

        fun deleteIncome(id: Int): Completable
    }
}