package rmnvich.apps.familybudget.presentation.fragment.categories.mvp

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.databinding.FragmentCategoriesBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.adapter.categories.CategoriesAdapter
import javax.inject.Inject

class FragmentCategories : Fragment(), FragmentCategoriesContract.View {

    private lateinit var binding: FragmentCategoriesBinding

    @Inject
    lateinit var mPresenter: FragmentCategoriesPresenter

    @Inject
    lateinit var mAdapter: CategoriesAdapter

    companion object {
        fun newInstance(): FragmentCategories {
            return FragmentCategories()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_categories,
                container, false)
        binding.handler = this

        (activity as DashboardActivity).toolbar.title =
                getString(R.string.title_categories)

        binding.recyclerCategories.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        mAdapter.setOnClickListener { mPresenter.onCategoryClicked(it) }
        binding.recyclerCategories.adapter = mAdapter

        return binding.root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        App.getApp(activity?.applicationContext).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPresenter.attachView(this)
        mPresenter.viewIsReady()
    }

    override fun onClickFab() {
        mPresenter.onFabClicked()
    }

    override fun updateAdapter(data: List<Category>) {
        mAdapter.setData(data)
    }

    override fun showProgress() {
        (activity as DashboardActivity).showProgress()
    }

    override fun hideProgress() {
        (activity as DashboardActivity).hideProgress()
    }

    override fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onDetach() {
        super.onDetach()
        App.getApp(activity?.applicationContext)
                .componentsHolder.releaseComponent(javaClass)
        mPresenter.detachView()
    }
}