package rmnvich.apps.familybudget.presentation.adapter.categories

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Category
import rmnvich.apps.familybudget.databinding.ItemCategoryBinding
import java.util.*

class CategoriesAdapter : RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {

    private lateinit var mListener: OnClickCategoryListener
    private var mCategoryList: List<Category> = LinkedList()

    fun setData(data: List<Category>) {
        val diffUtilCallback = CategoriesDiffUtilCallback(mCategoryList, data)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback, true)

        mCategoryList = data
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemCategoryBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_category, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mCategoryList[position])

        holder.binding.ivCategoryColor.circleColor =
                mCategoryList[position].color

        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return mCategoryList.size
    }

    interface OnClickCategoryListener {
        fun onClick(categoryId: Int)
    }

    fun setListener(listener: OnClickCategoryListener) {
        mListener = listener
    }

    inline fun setOnClickListener(
            crossinline onClickCategory: (Int) -> Unit) {

        setListener(object : OnClickCategoryListener {
            override fun onClick(categoryId: Int) {
                onClickCategory(categoryId)
            }
        })
    }

    inner class ViewHolder(var binding: ItemCategoryBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(category: Category) {
            binding.category = category;
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            mListener.onClick(mCategoryList[adapterPosition].categoryId)
        }
    }
}