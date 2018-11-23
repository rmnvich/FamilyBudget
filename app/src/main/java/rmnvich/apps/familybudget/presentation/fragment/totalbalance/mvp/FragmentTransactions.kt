package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

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
import rmnvich.apps.familybudget.databinding.FragmentTransactionsBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.adapter.totalbalance.TotalBalanceAdapter
import javax.inject.Inject

class FragmentTransactions : Fragment(), FragmentTransactionsContract.View {

    private lateinit var binding: FragmentTransactionsBinding

    @Inject
    lateinit var mPresenter: FragmentTransactionsPresenter

    @Inject
    lateinit var mAdapter: TotalBalanceAdapter

    companion object {
        fun newInstance(): FragmentTransactions {
            return FragmentTransactions()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transactions,
                container, false)
        binding.handler = this

        //TODO: added clickListener to adapter
        //TODO: added sort by timeRange
        //TODO: export data to Word/Excel

        (activity as DashboardActivity).toolbar.title = getString(R.string.title_total_balance)
        setHasOptionsMenu(true)

        binding.recyclerTransactions.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        binding.recyclerTransactions.adapter = mAdapter

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

    override fun updateAdapter(data: List<Any>) {
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