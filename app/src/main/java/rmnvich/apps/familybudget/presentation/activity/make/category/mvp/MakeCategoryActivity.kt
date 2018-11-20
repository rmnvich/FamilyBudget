package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log.d
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.WindowManager
import com.afollestad.materialdialogs.MaterialDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialog
import com.jaredrummler.android.colorpicker.ColorPickerDialogListener
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_CATEGORY_ID
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.databinding.ActivityMakeCategoryBinding
import javax.inject.Inject


class MakeCategoryActivity : AppCompatActivity(), MakeCategoryActivityContract.View,
        ColorPickerDialogListener {

    private lateinit var binding: ActivityMakeCategoryBinding

    @Inject
    lateinit var mPresenter: MakeCategoryActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_make_category)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    @Inject
    fun init() {
        val categoryId = intent.getIntExtra(EXTRA_CATEGORY_ID, -1)
        if (categoryId != -1)
            setSupportActionBar(binding.toolbar)

        mPresenter.attachView(this)
        mPresenter.setCategoryId(categoryId)
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

    override fun setToolbarColor(color: Int) {
        binding.toolbar.setBackgroundColor(color)
        window.addFlags(WindowManager.LayoutParams
                .FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window.statusBarColor = color
    }

    override fun setCategory(category: Category) {
        binding.category = category
        binding.invalidateAll()
    }

    override fun onClickApply() {
        mPresenter.onFabClicked(binding.category!!)
    }

    override fun onClickPickColor() {
        mPresenter.onPickColorClicked()
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

    override fun showColorPickerDialog() {
        ColorPickerDialog.newBuilder()
                .setAllowCustom(false)
                .setShowAlphaSlider(false)
                .show(this)
    }

    override fun onDialogDismissed(dialogId: Int) {}

    override fun onColorSelected(dialogId: Int, color: Int) {
        binding.category?.color = color
        setToolbarColor(color)
    }

    override fun showProgress() {
        binding.progressBar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.progressBar.visibility = View.GONE
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
