package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import io.reactivex.Flowable
import rmnvich.apps.familybudget.domain.mvp.MvpModel
import rmnvich.apps.familybudget.domain.mvp.MvpPresenter
import rmnvich.apps.familybudget.domain.mvp.MvpView

interface FragmentTransactionsContract {

    interface View : MvpView {

        fun updateAdapter(data: List<Any>)
    }

    interface Presenter : MvpPresenter<View> {

        fun getAllTransactions()
    }

    interface Model : MvpModel {

        fun getAllTransactions(): Flowable<List<Any>>
    }
}