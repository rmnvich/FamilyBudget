package rmnvich.apps.familybudget.presentation.activity.make.category.mvp

import android.databinding.DataBindingUtil
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.databinding.ActivityMakeCategoryBinding

class MakeCategoryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMakeCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_make_category)
        binding.handler = this

        App.getApp(this).componentsHolder
                .getComponent(javaClass).inject(this)
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getApp(this).componentsHolder
                .releaseComponent(javaClass)
    }
}
