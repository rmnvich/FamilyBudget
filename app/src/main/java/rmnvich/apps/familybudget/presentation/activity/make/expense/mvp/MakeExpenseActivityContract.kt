package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface MakeExpenseActivityContract {

    interface View : MvpView {

        fun setExpense(expense: Expense)

        fun setTimestamp(timestamp: Long)

        fun setCategory(category: Category)

        fun showConfirmDialog()

        fun showDatePickerDialog()

        fun showCategoryDialog()

        fun onClickApply()

        fun onClickPickDate()

        fun onClickSelectCategory()
    }

    interface Presenter : MvpPresenter<View> {

        fun setExpenseId(id: Int)

        fun onFabClicked(expense: Expense)

        fun onDialogConfirm()

        fun onClickDelete()

        fun onSelectCategoryClicked()

        fun onPickDateClicked()

        fun onCategorySelected(categoryId: Int)

        fun onDatePickerDialogClicked(year: Int, month: Int, day: Int)

        fun isDataCorrect(expense: Expense): Boolean
    }

    interface Model : MvpModel {

        fun getExpenseById(id: Int): Single<Expense>

        fun getCategoryById(id: Int): Single<Category>

        fun insertExpense(expense: Expense, expenseId: Int): Completable

        fun deleteExpense(id: Int): Completable
    }
}