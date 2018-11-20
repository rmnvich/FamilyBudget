package rmnvich.apps.familybudget.presentation.activity.dashboard.mvp

import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v4.widget.DrawerLayout
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.app_bar_main.view.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.common.Constants.EXTRA_USER_ID
import rmnvich.apps.familybudget.data.entity.Balance
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.ActivityDashboardBinding
import rmnvich.apps.familybudget.databinding.NavHeaderDashboardBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.dagger.DashboardActivityModule
import rmnvich.apps.familybudget.presentation.dialog.InitBalanceDialog
import rmnvich.apps.familybudget.presentation.fragment.categories.mvp.FragmentCategories
import rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp.FragmentFamilyMembers
import java.io.File
import javax.inject.Inject
import javax.inject.Provider

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
        DashboardActivityContract.View {

    private lateinit var binding: ActivityDashboardBinding
    private lateinit var navHeaderBinding: NavHeaderDashboardBinding

    private lateinit var mActiveFragment: Fragment

    @Inject
    lateinit var mPresenter: DashboardActivityPresenter

    @Inject
    lateinit var mInitBalanceDialog: Provider<InitBalanceDialog>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,
                R.layout.activity_dashboard)
        binding.handler = this

        navHeaderBinding = DataBindingUtil.inflate(layoutInflater,
                R.layout.nav_header_dashboard, binding.navView, false)
        binding.navView.addHeaderView(navHeaderBinding.root)

        App.getApp(this).componentsHolder.getComponent(javaClass,
                DashboardActivityModule(this)).inject(this)
    }

    @Inject
    fun init() {
        setSupportActionBar(toolbar)
        val toggle = ActionBarDrawerToggle(this, drawer_layout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        mActiveFragment = FragmentFamilyMembers.newInstance()
        showFragment(mActiveFragment)

        drawer_layout.addDrawerListener(object : DrawerLayout.DrawerListener {
            override fun onDrawerStateChanged(newState: Int) {}
            override fun onDrawerSlide(drawerView: View, slideOffset: Float) {}
            override fun onDrawerOpened(drawerView: View) {}
            override fun onDrawerClosed(drawerView: View) {
                showFragment(mActiveFragment)
            }
        })

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.setCheckedItem(R.id.nav_family_members)

        mPresenter.attachView(this)
        mPresenter.setUserId(intent.extras.getInt(EXTRA_USER_ID))
        mPresenter.viewIsReady()
    }

    override fun setUser(user: User) {
        navHeaderBinding.user = user

        if (!user.photoPath.isEmpty()) {
            Glide.with(this)
                    .load(File(user.photoPath))
                    .into(navHeaderBinding.imageView)
            navHeaderBinding.invalidateAll()
        }
    }

    override fun setBalance(balance: Balance) {
        navHeaderBinding.balance = balance
        navHeaderBinding.invalidateAll()
    }

    override fun updateBalance() {
        mPresenter.updateBalance()
    }

    override fun showInitialBalanceDialog() {
        mInitBalanceDialog.get().show {
            mPresenter.onApplyBalanceDialogClicked(it)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_family_members -> mActiveFragment = FragmentFamilyMembers.newInstance()
            R.id.nav_planned_expenses -> {
            }
            R.id.nav_actual_expenses -> {
            }
            R.id.nav_incomes -> {
            }
            R.id.nav_total_balance -> {
            }
            R.id.nav_categories -> mActiveFragment = FragmentCategories.newInstance()
            R.id.nav_edit_profile -> mPresenter.onEditProfileClicked()
            R.id.nav_logout -> mPresenter.onLogoutClicked()
        }
        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    override fun showFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out)
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun showProgress() {
        binding.appBarMain.progress_bar.visibility = View.VISIBLE
    }

    override fun hideProgress() {
        binding.appBarMain.progress_bar.visibility = View.INVISIBLE
    }

    override fun showMessage(text: String) {
        Snackbar.make(binding.root, text, Snackbar.LENGTH_LONG).show()
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else super.onBackPressed()
    }

    override fun onDestroy() {
        super.onDestroy()
        App.getApp(this).componentsHolder
                .releaseComponent(javaClass)
        mPresenter.detachView()
    }
}
