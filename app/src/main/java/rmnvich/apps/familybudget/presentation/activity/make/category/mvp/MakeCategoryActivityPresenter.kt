package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import android.os.Handler
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants.DEFAULT_COLOR
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.domain.interactor.mvp.PresenterBase

class MakeCategoryActivityPresenter(private val model: MakeCategoryActivityModel,
                                    private val compositeDisposable: CompositeDisposable) :
        PresenterBase<MakeCategoryActivityContract.View>(), MakeCategoryActivityContract.Presenter {

    private var categoryId = -1

    override fun viewIsReady() {
        if (categoryId != -1) {
            view?.showProgress()
            val disposable = model.getCategoryById(categoryId)
                    .subscribe({
                        view?.hideProgress()
                        view?.setCategory(it)
                        if (it.color != DEFAULT_COLOR)
                            view?.setToolbarColor(it.color)
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.setCategory(Category())
    }

    override fun onFabClicked(category: Category) {
        if (isDataCorrect(category)) {
            view?.showProgress()
            val disposable = model.insertCategory(category)
                    .subscribe({
                        view?.hideProgress()
                        (view as MakeCategoryActivity).finish()
                    }, {
                        view?.hideProgress()
                        view?.showMessage(getString(R.string.error))
                    })
            compositeDisposable.add(disposable)
        } else view?.showMessage(getString(R.string.empty_field_error))
    }

    override fun onClickDelete() {
        Handler().postDelayed({
            view?.showConfirmDialog()
        }, 200)
    }

    override fun onDialogConfirm() {
        view?.showProgress()
        val disposable = model.deleteCategory(categoryId)
                .subscribe({
                    view?.hideProgress()
                    (view as MakeCategoryActivity).finish()
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun isDataCorrect(category: Category): Boolean {
        return !category.name.isEmpty()
    }

    override fun onPickColorClicked() {
        Handler().postDelayed({
            view?.showColorPickerDialog()
        }, 250)
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }

    override fun setCategoryId(id: Int) {
        categoryId = id
    }
}