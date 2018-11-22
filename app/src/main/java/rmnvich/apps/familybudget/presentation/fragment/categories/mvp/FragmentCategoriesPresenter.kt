package rmnvich.apps.familybudget.presentation.fragment.categories.mvp

import android.content.Intent
import io.reactivex.disposables.CompositeDisposable
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_CATEGORY_ID
import rmnvich.apps.familybudget.domain.mvp.PresenterBase
import rmnvich.apps.familybudget.presentation.activity.make.category.mvp.MakeCategoryActivity

class FragmentCategoriesPresenter(private val model: FragmentCategoriesModel,
                                  private val compositeDisposable: CompositeDisposable) :
        PresenterBase<FragmentCategoriesContract.View>(), FragmentCategoriesContract.Presenter {

    override fun viewIsReady() {
        view?.showProgress()
        val disposable = model.getAllCategories()
                .subscribe({
                    view?.hideProgress()
                    view?.updateAdapter(it)
                }, {
                    view?.hideProgress()
                    view?.showMessage(getString(R.string.error))
                })
        compositeDisposable.add(disposable)
    }

    override fun onFabClicked() {
        (view as FragmentCategories).startActivity(Intent((view as FragmentCategories).activity,
                MakeCategoryActivity::class.java))
    }

    override fun onCategoryClicked(id: Int) {
        (view as FragmentCategories).startActivity(Intent((view as FragmentCategories).activity,
                MakeCategoryActivity::class.java)
                .putExtra(EXTRA_CATEGORY_ID, id))
    }

    override fun detachView() {
        super.detachView()
        compositeDisposable.dispose()
    }
}