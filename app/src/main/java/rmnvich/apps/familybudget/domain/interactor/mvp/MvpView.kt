package rmnvich.apps.familybudget.domain.interactor.mvp

interface MvpView {

    fun showProgress()

    fun hideProgress()

    fun showMessage(text: String)
}
