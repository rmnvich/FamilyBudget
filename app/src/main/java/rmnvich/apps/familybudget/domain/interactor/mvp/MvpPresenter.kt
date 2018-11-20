package rmnvich.apps.familybudget.domain.interactor.mvp

interface MvpPresenter<V : MvpView> {

    fun attachView(mvpView: V)

    fun viewIsReady()

    fun detachView()
}