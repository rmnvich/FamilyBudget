package rmnvich.apps.familybudget.presentation.activity.editprofile.mvp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import com.bumptech.glide.Glide
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.ActivityEditProfileBinding
import java.io.File
import javax.inject.Inject

class EditProfileActivity : AppCompatActivity(), EditProfileActivityContract.View {

    private lateinit var binding: ActivityEditProfileBinding

    @Inject
    lateinit var mPresenter: EditProfileActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_edit_profile)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    @Inject
    fun init() {
        mPresenter.attachView(this)
        mPresenter.setUserId(intent.getIntExtra(
                EXTRA_USER_ID, -1))
        mPresenter.viewIsReady()
    }

    override fun setUser(user: User) {
        binding.user = user

        Glide.with(this)
                .load(File(user.photoPath))
                .into(binding.ivPhoto)
        binding.invalidateAll()
    }

    override fun onClickApply() {
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
