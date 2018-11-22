package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.common.helper.DateHelper
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class MakeExpenseActivityPresenter(private val model: MakeExpenseActivityModel,
                                   private val compositeDisposable: CompositeDisposable) :
        PresenterBase<MakeExpenseActivityContract.View>(), MakeExpenseActivityContract.Presenter {

    private var mExpenseId: Int = -1
    private var mExpenseValue: String = "0"

    override fun viewIsReady() {
        if (mExpenseId != -1) {
            view?.showProgress()
            val disposable = model.getExpenseById(mExpenseId)
                    .subscribe({
                        view?.hideProgress()
                        view?.setExpense(it)

                        mExpenseValue = it.value
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.setExpense(Expense())
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun setExpenseId(id: Int) {
        mExpenseId = id
    }

    override fun onFabClicked(expense: Expense) {
        if (isDataCorrect(expense)) {
            val disposable = model.insertExpense(expense, mExpenseValue)
                    .subscribe({
                        view?.hideProgress()
                        (view as MakeExpenseActivity).finish()
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.showMessage(getString(R.string.empty_field_error))
    }

    override fun onDialogConfirm() {
        view?.showProgress()
        val disposable = model.deleteExpense(mExpenseId)
                .subscribe({
                    view?.hideProgress()
                    (view as MakeExpenseActivity).finish()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onCategorySelected(categoryId: Int) {
        view?.showProgress()
        val disposable = model.getCategoryById(categoryId)
                .subscribe({
                    view?.hideProgress()
                    view?.setCategory(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onDatePickerDialogClicked(year: Int, month: Int, day: Int) {
        view?.setTimestamp(DateHelper.getTimeFromDatePicker(year, month, day))
    }

    override fun onSelectCategoryClicked() {
        Handler().postDelayed({
            view?.showCategoryDialog()
        }, 200)
    }

    override fun onClickDelete() {
        Handler().postDelayed({
            view?.showConfirmDialog()
        }, 200)
    }

    override fun onPickDateClicked() {
        Handler().postDelayed({
            view?.showDatePickerDialog()
        }, 200)
    }

    override fun isDataCorrect(expense: Expense): Boolean {
        return !(expense.value.isEmpty() ||
                expense.category == null)
    }
}