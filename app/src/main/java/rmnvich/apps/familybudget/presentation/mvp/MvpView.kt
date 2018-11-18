package rmnvich.apps.familybudget.presentation.mvp

interface MvpView {

    fun showProgress()

    fun hideProgress()

    fun showMessage(text: String)
}
