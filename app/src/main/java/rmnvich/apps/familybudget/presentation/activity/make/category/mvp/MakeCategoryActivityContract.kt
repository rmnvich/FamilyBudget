package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import rmnvich.apps.familybudget.domain.interactor.mvp.MvpModel
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.interactor.mvp.MvpView

interface MakeCategoryActivityContract {

    interface View: MvpView {

    }

    interface Presenter: MvpPresenter<View> {

    }

    interface Model : MvpModel {

    }
}