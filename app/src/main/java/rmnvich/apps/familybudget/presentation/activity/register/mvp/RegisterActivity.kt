package rmnvich.apps.familybudget.presentation.activity.register.mvp

import android.app.Activity
import android.content.Intent
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.bumptech.glide.Glide
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants.REQUEST_CODE_PHOTO
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.ActivityRegisterBinding
import java.io.File
import javax.inject.Inject

class RegisterActivity : AppCompatActivity(), RegisterActivityContract.View {

    private lateinit var binding: ActivityRegisterBinding

    @Inject
    lateinit var mPresenter: RegisterActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_register)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    @Inject
    fun init() {
        mPresenter.attachView(this)
        mPresenter.viewIsReady()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(android.R.anim.fade_in,
                android.R.anim.fade_out)
    }

    override fun setUser(user: User) {
        binding.user = user
    }

    override fun setImageView(photoPath: String) {
        binding.user?.photoPath = photoPath

        Glide.with(this)
                .load(File(photoPath))
                .into(binding.ivPhoto)
    }

    override fun onClickImageView() {
        mPresenter.onImageViewClicked()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_PHOTO && resultCode ==
                Activity.RESULT_OK && data != null
        ) {
            mPresenter.onActivityResult(data)
        }
    }

    override fun onClickRegister() {
        mPresenter.onRegisterClicked(binding.user!!)
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
