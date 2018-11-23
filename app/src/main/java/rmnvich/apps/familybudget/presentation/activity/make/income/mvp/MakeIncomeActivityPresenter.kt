package rmnvich.apps.familybudget.presentation.activity.make.income.mvp

import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.domain.mvp.PresenterBase

class MakeIncomeActivityPresenter(private val model: MakeIncomeActivityModel,
                                  private val compositeDisposable: CompositeDisposable) :
        PresenterBase<MakeIncomeActivityContract.View>(), MakeIncomeActivityContract.Presenter {

    private var mIncomeId = -1
    private var mIncomeValue = "0"
    private var mIncomeType = ""

    override fun viewIsReady() {
        getIncomeById()
        getUserById()
    }

    override fun getIncomeById() {
        if (mIncomeId != -1) {
            view?.showProgress()
            val disposable = model.getIncomeById(mIncomeId)
                    .subscribe({
                        view?.hideProgress()

                        view?.setIncome(it)
                        mIncomeValue = it.value
                        mIncomeType = it.incomeType
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.setIncome(Income())
    }

    override fun getUserById() {
        view?.showProgress()
        val disposable = model.getUserById()
                .subscribe({
                    val allIncomeTypes = (view as MakeIncomeActivity).resources
                            .getStringArray(R.array.income_types)
                    val userIncomeTypes = mutableListOf<String>()
                    for (i in it.incomeTypeIds) {
                        userIncomeTypes.add(allIncomeTypes[i])
                    }

                    var selectedIncomeType = 0
                    for ((index, value) in userIncomeTypes.withIndex()) {
                        if (value == mIncomeType) {
                            selectedIncomeType = index
                            break
                        }
                    }
                    view?.hideProgress()
                    view?.setSpinnerAdapter(userIncomeTypes, selectedIncomeType)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun setIncomeId(id: Int) {
        mIncomeId = id
    }

    override fun onFabClicked(income: Income) {
        if (isDataCorrect(income)) {
            val disposable = model.insertIncome(income, mIncomeValue)
                    .subscribe({
                        view?.hideProgress()
                        (view as MakeIncomeActivity).finish()
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.showMessage(getString(R.string.empty_field_error))
    }

    override fun onDialogConfirm() {
        view?.showProgress()
        val disposable = model.deleteIncome(mIncomeId)
                .subscribe({
                    view?.hideProgress()
                    (view as MakeIncomeActivity).finish()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onClickDelete() {
        Handler().postDelayed({
            view?.showConfirmDialog()
        }, 200)
    }

    override fun isDataCorrect(income: Income): Boolean {
        return !(income.value.isEmpty() ||
                income.incomeType.isEmpty())
    }
}