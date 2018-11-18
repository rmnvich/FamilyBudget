package rmnvich.apps.familybudget.presentation.activity.login.mvp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.view.View
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.databinding.ActivityLoginBinding
import javax.inject.Inject

class LoginActivity : AppCompatActivity(), LoginActivityContract.View {

    private lateinit var binding: ActivityLoginBinding

    @Inject
    lateinit var mPresenter: LoginActivityPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_login)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    @Inject
    fun init() {
        mPresenter.attachView(this)
        mPresenter.viewIsReady()
    }

    override fun onClickLogin() {
        mPresenter.onLoginClicked(binding.etLogin.text.toString(),
                binding.etPassword.text.toString())
    }

    override fun onClickRegister() {
        mPresenter.onRegisterClicked()
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
