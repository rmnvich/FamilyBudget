package rmnvich.apps.familybudget.presentation.adapter.familymembers

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import com.bumptech.glide.Glide
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.User
import rmnvich.apps.familybudget.databinding.ItemFamilyMemberBinding
import java.io.File
import java.util.*

class FamilyMembersAdapter : RecyclerView.Adapter<FamilyMembersAdapter.ViewHolder>() {

    private var mUserList: List<User> = LinkedList()

    fun setData(data: List<User>) {
        val diffUtilCallback = FamilyMembersDiffUtilCallback(mUserList, data)
        val diffResult = DiffUtil.calculateDiff(diffUtilCallback, true)

        mUserList = data
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemFamilyMemberBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_family_member, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mUserList[position])

        Glide.with(holder.itemView.context)
                .load(File(mUserList[position].photoPath))
                .into(holder.binding.ivUserPhoto)

        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return mUserList.size
    }

    inner class ViewHolder(var binding: ItemFamilyMemberBinding) :
            RecyclerView.ViewHolder(binding.root) {

        fun bind(user: User) {
            binding.user = user
            binding.executePendingBindings()
        }
    }
}