package rmnvich.apps.familybudget.presentation.fragment.totalbalance.mvp

import android.content.Context
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.*
import com.borax12.materialdaterangepicker.date.DatePickerDialog
import kotlinx.android.synthetic.main.app_bar_main.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.databinding.FragmentTransactionsBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.adapter.totalbalance.TotalBalanceAdapter
import javax.inject.Inject

class FragmentTransactions : Fragment(), FragmentTransactionsContract.View, DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentTransactionsBinding

    @Inject
    lateinit var mPresenter: FragmentTransactionsPresenter

    @Inject
    lateinit var mAdapter: TotalBalanceAdapter

    private var timeRangeStart: Long = 0L
    private var timeRangeEnd: Long = 0L

    companion object {
        fun newInstance(): FragmentTransactions {
            return FragmentTransactions()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_transactions,
                container, false)
        binding.handler = this

        //TODO: export data to Word/Excel

        (activity as DashboardActivity).toolbar.title = getString(R.string.title_total_balance)
        setHasOptionsMenu(true)

        binding.recyclerTransactions.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        mAdapter.setOnClickListener { transactionId, transactionType ->
            mPresenter.onItemClicked(transactionId, transactionType)
        }
        binding.recyclerTransactions.adapter = mAdapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.total_balance_menu, menu)
        val filterMenu = menu?.findItem(R.id.filter)
        filterMenu?.setOnMenuItemClickListener {
            mPresenter.onFilterClicked()
            true
        }
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

    override fun showDatePickerDialog(year: Int, month: Int, day: Int) {
        val pickerDialog = DatePickerDialog.newInstance(
                this, year, month, day)
        pickerDialog.setStartTitle(getString(R.string.date_picker_title_start))
        pickerDialog.setEndTitle(getString(R.string.date_picker_title_end))
        pickerDialog.show(activity!!.fragmentManager, "")
    }

    override fun onDateSet(view: DatePickerDialog?, year: Int, monthOfYear: Int, dayOfMonth: Int,
                           yearEnd: Int, monthOfYearEnd: Int, dayOfMonthEnd: Int) {
        mPresenter.onDateSet(year, monthOfYear, dayOfMonth, yearEnd, monthOfYearEnd, dayOfMonthEnd)
    }

    override fun saveTimeRange(timeRangeStart: Long, timeRangeEnd: Long) {
        this.timeRangeStart = timeRangeStart
        this.timeRangeEnd = timeRangeEnd
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (timeRangeStart == 0L && timeRangeEnd == 0L)
            mPresenter.getAllTransactions()
        else mPresenter.getSortedTransactions(timeRangeStart, timeRangeEnd)
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