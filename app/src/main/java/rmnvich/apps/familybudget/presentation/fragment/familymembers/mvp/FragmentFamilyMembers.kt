package rmnvich.apps.familybudget.presentation.fragment.familymembers.mvp

import android.content.Context
import android.databinding.DataBindingUtil
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.app_bar_main.*
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.app.App
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.FragmentFamilyMembersBinding
import rmnvich.apps.familybudget.presentation.activity.dashboard.mvp.DashboardActivity
import rmnvich.apps.familybudget.presentation.adapter.familymembers.FamilyMembersAdapter
import javax.inject.Inject

class FragmentFamilyMembers : Fragment(), FragmentFamilyMembersContract.View {

    private lateinit var binding: FragmentFamilyMembersBinding

    @Inject
    lateinit var mPresenter: FragmentFamilyMembersPresenter

    @Inject
    lateinit var mAdapter: FamilyMembersAdapter

    companion object {
        fun newInstance(): FragmentFamilyMembers {
            return FragmentFamilyMembers()
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_family_members, container, false)
        binding.handler = this

        (activity as DashboardActivity).toolbar.title =
                getString(R.string.title_family_members)

        binding.recyclerFamilyMembers.layoutManager = LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false)
        binding.recyclerFamilyMembers.adapter = mAdapter

        return binding.root
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

    override fun updateAdapter(data: List<User>) {
        mAdapter.setData(data)
    }

    override fun showProgress() {
        (activity as DashboardActivity).showProgress()
    }

    override fun hideProgress() {
        (activity as DashboardActivity).hideProgress()
    }

    override fun showMessage(text: String) {
        (activity as DashboardActivity).showMessage(text)
    }

    override fun onDetach() {
        super.onDetach()
        App.getApp(activity?.applicationContext)
                .componentsHolder.releaseComponent(javaClass)
        mPresenter.detachView()
    }
}