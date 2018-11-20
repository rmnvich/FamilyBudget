package rmnvich.apps.familybudget.presentation.adapter.expenses

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.databinding.ItemActualExpenseBinding
import java.util.*

class ActualExpensesAdapter : RecyclerView.Adapter<ActualExpensesAdapter.ViewHolder>() {

    private lateinit var mListener: OnClickExpenseListener
    private var mExpenseList: List<Expense> = LinkedList()

    fun setData(data: List<Expense>) {
        val diffUtilCallback = ExpensesDiffUtilCallback(mExpenseList, data)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback, true)

        mExpenseList = data
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding: ItemActualExpenseBinding = DataBindingUtil.inflate(inflater,
                R.layout.item_actual_expense, parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(mExpenseList[position])

        holder.binding.ivCategoryColor.circleColor =
                mExpenseList[position].category?.color!!

        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
    }

    override fun getItemCount(): Int {
        return mExpenseList.size
    }

    interface OnClickExpenseListener {
        fun onClick(expenseId: Int)
    }

    fun setListener(listener: OnClickExpenseListener) {
        mListener = listener
    }

    inline fun setOnClickListener(
            crossinline onClickExpense: (Int) -> Unit) {

        setListener(object : OnClickExpenseListener {
            override fun onClick(expenseId: Int) {
                onClickExpense(expenseId)
            }
        })
    }

    inner class ViewHolder(var binding: ItemActualExpenseBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(expense: Expense) {
            binding.expense = expense
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            mListener.onClick(mExpenseList[adapterPosition].expenseId)
        }
    }
}