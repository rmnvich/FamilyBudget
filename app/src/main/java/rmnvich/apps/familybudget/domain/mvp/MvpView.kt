package rmnvich.apps.familybudget.domain.mvp

interface MvpView {

    fun showProgress()

    fun hideProgress()

    fun showMessage(text: String)
}
