package rmnvich.apps.familybudget.presentation.fragment.plannedexpenses.mvp

import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.domain.interactor.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.expense.mvp.MakeExpenseActivity

class FragmentPlannedExpensesPresenter(private val model: FragmentPlannedExpensesModel,
                                       private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentPlannedExpensesContract.View>(), FragmentPlannedExpensesContract.Presenter {

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getAllPlannedExpenses()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onApplyExpenseClicked(id: Int) {
        view?.showProgress()
        val disposable = model.updateExpense(id)
                .subscribe({
                    view?.hideProgress()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onFabClicked() {
        (view as FragmentPlannedExpenses).startActivity(Intent((view as FragmentPlannedExpenses).activity,
                MakeExpenseActivity::class.java))
    }

    override fun onExpenseClicked(id: Int) {
        (view as FragmentPlannedExpenses).startActivity(Intent((view as FragmentPlannedExpenses).activity,
                MakeExpenseActivity::class.java)
                .putExtra(Constants.EXTRA_EXPENSE_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}