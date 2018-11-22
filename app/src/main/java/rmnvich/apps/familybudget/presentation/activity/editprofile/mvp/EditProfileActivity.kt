package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import com.bumptech.glide.Glide
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.ActivityEditProfileBinding
import rmnvich.apps.familybudget.presentation.activity.editprofile.dagger.EditProfileActivityModule
import rmnvich.apps.familybudget.presentation.adapter.incometypes.IncomeTypesAdapter
import java.io.File
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity(), EditProfileActivityContract.View {

    private lateinit var binding: ActivityEditProfileBinding

    @Inject
    lateinit var mPresenter: EditProfileActivityPresenter

    @Inject
    lateinit var mAdapter: IncomeTypesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_edit_profile)
        binding.handler = this

        App.getApp(this).componentsHolder.getComponent(javaClass,
                EditProfileActivityModule(this)).inject(this)
    }

    @Inject
    fun init() {
        binding.recyclerIncomeTypes.layoutManager = LinearLayoutManager(this,
                LinearLayoutManager.VERTICAL, false)
        binding.recyclerIncomeTypes.adapter = mAdapter
        mAdapter.setOnClickListener {}

        mPresenter.attachView(this)
        mPresenter.setUserId(intent.getIntExtra(
                EXTRA_USER_ID, -1))
        mPresenter.viewIsReady()
    }

    override fun setUser(user: User) {
        binding.user = user
        setImageView(user.photoPath)

        if (!user.incomeTypeIds.isEmpty()) {
            mAdapter.checkedPosition = user.incomeTypeIds as MutableList<Int>
            mAdapter.notifyDataSetChanged()
        }
    }

    override fun onClickImageView() {
        mPresenter.onImageViewClicked()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Constants.REQUEST_CODE_PHOTO && resultCode ==
                Activity.RESULT_OK && data != null)
            mPresenter.onActivityResult(data)
    }

    override fun setImageView(photoPath: String) {
        binding.user?.photoPath = photoPath

        Glide.with(this)
                .load(File(photoPath))
                .into(binding.ivPhoto)
        binding.invalidateAll()
    }

    override fun onClickApply() {
        binding.user?.incomeTypeIds = mAdapter.checkedPosition
        mPresenter.onApplyClicked(binding.user!!)
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
