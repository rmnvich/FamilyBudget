package rmnvich.apps.familybudget.presentation.activity.make.income.mvp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ArrayAdapter
import com.afollestad.materialdialogs.MaterialDialog
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.databinding.ActivityMakeIncomeBinding
import javax.inject.Inject

class MakeIncomeActivity : AppCompatActivity(), MakeIncomeActivityContract.View {

    private lateinit var binding: ActivityMakeIncomeBinding

    @Inject
    lateinit var mPresenter: MakeIncomeActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_make_income)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    @Inject
    fun init() {
        val incomeId = intent.getIntExtra(Constants.EXTRA_INCOME_ID, -1)
        if (incomeId != -1)
            setSupportActionBar(binding.toolbar)

        mPresenter.attachView(this)
        mPresenter.setIncomeId(incomeId)
        mPresenter.viewIsReady()
    }


    override fun setIncome(income: Income) {
        binding.income = income
        binding.invalidateAll()
    }

    override fun setSpinnerAdapter(incomeTypes: List<String>, selectedItemPosition: Int) {
        val adapter = ArrayAdapter(this,
                android.R.layout.simple_spinner_item, incomeTypes)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.spinnerIncomeType.adapter = adapter

        try {
            binding.spinnerIncomeType.setSelection(selectedItemPosition, true)
        } catch (e: IndexOutOfBoundsException) {
            binding.spinnerIncomeType.setSelection(0)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.delete_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        return when (item?.itemId) {
            R.id.delete -> {
                mPresenter.onClickDelete()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun showConfirmDialog() {
        MaterialDialog.Builder(this)
                .title(getString(R.string.confirm_dialog_title))
                .content(getString(R.string.confirm_dialog_message))
                .positiveText(getString(R.string.confirm_dialog_positive))
                .negativeText(getString(R.string.confirm_dialog_negative))
                .onNegative { dialog, _ -> dialog.dismiss() }
                .onPositive { _, _ -> mPresenter.onDialogConfirm() }
                .show()
    }

    override fun onClickApply() {
        try {
            binding.income?.incomeType = binding.spinnerIncomeType.selectedItem.toString()
        } catch (ignored: NullPointerException) {
            binding.income?.incomeType = ""
        }
        mPresenter.onFabClicked(binding.income!!)
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.INVISIBLE
    }

    override fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getApp(this).componentsHolder
                .releaseComponent(javaClass)
        mPresenter.detachView()
    }
}
