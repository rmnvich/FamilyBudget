package rmnvich.apps.familybudget.presentation.activity.make.expense.mvp

import android.app.DatePickerDialog
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.afollestad.materialdialogs.MaterialDialog
import dagger.Lazy
import kotlinx.android.synthetic.main.item_category.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_EXPENSE_ID
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.databinding.ActivityMakeExpenseBinding
import rmnvich.apps.familybudget.presentation.activity.make.expense.dagger.MakeExpenseActivityModule
import rmnvich.apps.familybudget.presentation.dialog.DialogCategories
import java.util.*
import javax.inject.Inject

class MakeExpenseActivity : AppCompatActivity(), MakeExpenseActivityContract.View {

    private lateinit var binding: ActivityMakeExpenseBinding

    @Inject
    lateinit var mPresenter: MakeExpenseActivityPresenter

    @Inject
    lateinit var mCategoriesDialog: Lazy<DialogCategories>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_make_expense)
        binding.handler = this

        App.getApp(this).componentsHolder.getComponent(javaClass,
                MakeExpenseActivityModule(this)).inject(this)
    }

    @Inject
    fun init() {
        val expenseId = intent.getIntExtra(EXTRA_EXPENSE_ID, -1)
        if (expenseId != -1)
            setSupportActionBar(binding.toolbar)

        mPresenter.attachView(this)
        mPresenter.setExpenseId(expenseId)
        mPresenter.viewIsReady()
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

    override fun setExpense(expense: Expense) {
        if (expense.category != null)
            iv_category_color.circleColor = expense.category?.color!!
        binding.expense = expense
        binding.invalidateAll()
    }

    override fun setTimestamp(timestamp: Long) {
        binding.expense?.timestamp = timestamp
        binding.invalidateAll()
    }

    override fun setCategory(category: Category) {
        iv_category_color.circleColor = category.color
        binding.expense?.category = category
        binding.invalidateAll()
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

    override fun showDatePickerDialog() {
        DatePickerDialog(this, { _, year, month, day ->
            mPresenter.onDatePickerDialogClicked(year, month, day)
        }, Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
                .show()
    }

    override fun showCategoryDialog() {
        mCategoriesDialog.get().show {
            mPresenter.onCategorySelected(it)
        }
    }

    override fun onClickApply() {
        mPresenter.onFabClicked(binding.expense!!)
    }

    override fun onClickPickDate() {
        mPresenter.onPickDateClicked()
    }

    override fun onClickSelectCategory() {
        mPresenter.onSelectCategoryClicked()
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
