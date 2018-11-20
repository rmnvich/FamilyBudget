package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import io.reactivex.Completable
import io.reactivex.Single
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface MakeCategoryActivityContract {

    interface View : MvpView {

        fun setToolbarColor(color: Int)

        fun setCategory(category: Category)

        fun showColorPickerDialog()

        fun showConfirmDialog()

        fun onClickApply()

        fun onClickPickColor()
    }

    interface Presenter : MvpPresenter<View> {

        fun setCategoryId(id: Int)

        fun onFabClicked(category: Category)

        fun onDialogConfirm()

        fun onClickDelete()

        fun onPickColorClicked()

        fun isDataCorrect(category: Category): Boolean
    }

    interface Model : MvpModel {

        fun getCategoryById(id: Int): Single<Category>

        fun insertCategory(category: Category): Completable

        fun deleteCategory(id: Int): Completable
    }
}