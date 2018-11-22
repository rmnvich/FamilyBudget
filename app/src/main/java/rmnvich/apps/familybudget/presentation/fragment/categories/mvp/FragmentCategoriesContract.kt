package rmnvich.apps.familybudget.presentation.fragment.categories.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentCategoriesContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Category>)

        fun onClickFab()
    }

    interface Presenter : MvpPresenter<View> {

        fun onFabClicked()

        fun onCategoryClicked(id: Int)
    }

    interface Model : MvpModel {

        fun getAllCategories(): Flowable<List<Category>>
    }
}