package rmnvich.apps.familybudget.presentation.fragment.incomes.mvp

import android.content.Context
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
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.databinding.FragmentIncomesBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.adapter.incomes.IncomesAdapter
import javax.inject.Inject

class FragmentIncomes : Fragment(), FragmentIncomesContract.View, DatePickerDialog.OnDateSetListener {

    private lateinit var binding: FragmentIncomesBinding

    @Inject
    lateinit var mPresenter: FragmentIncomesPresenter

    @Inject
    lateinit var mAdapter: IncomesAdapter

    companion object {
        fun newInstance(): FragmentIncomes {
            return FragmentIncomes()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_incomes,
                container, false)
        binding.handler = this

        (activity as DashboardActivity).toolbar.title = getString(R.string.title_incomes)
        setHasOptionsMenu(true)

        binding.recyclerIncomes.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        mAdapter.setOnClickListener { mPresenter.onIncomeClicked(it) }
        binding.recyclerIncomes.adapter = mAdapter

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.filter_menu, menu)
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

    override fun onClickFab() {
        mPresenter.onFabClicked()
    }

    override fun updateAdapter(data: List<Income>) {
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