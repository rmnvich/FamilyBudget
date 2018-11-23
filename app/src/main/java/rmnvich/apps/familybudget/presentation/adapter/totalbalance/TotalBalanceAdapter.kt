package rmnvich.apps.familybudget.presentation.adapter.totalbalance

import android.databinding.DataBindingUtil
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.common.Constants
import rmnvich.apps.familybudget.data.common.Constants.TYPE_EXPENSE
import rmnvich.apps.familybudget.data.common.Constants.TYPE_INCOME
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.databinding.ItemActualExpenseBinding
import rmnvich.apps.familybudget.databinding.ItemIncomeBinding
import rmnvich.apps.familybudget.presentation.adapter.expenses.ExpensesDiffUtilCallback
import java.util.*

class TotalBalanceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var mClickListener: (Int, Int) -> Unit
    private var mTransactions: List<Any> = LinkedList()

    fun setData(data: List<Any>) {
        val diffUtilCallback = TotalBalanceDiffUtilCallback(mTransactions, data)
        val diffUtilResult = DiffUtil.calculateDiff(diffUtilCallback, true)

        mTransactions = data
        diffUtilResult.dispatchUpdatesTo(this)
    }

    override fun getItemViewType(position: Int): Int {
        return if (mTransactions[position] is Income) {
            TYPE_INCOME
        } else TYPE_EXPENSE
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_INCOME -> {
                val binding: ItemIncomeBinding = DataBindingUtil.inflate(inflater,
                        R.layout.item_income, parent, false)
                IncomesViewHolder(binding)
            }
            else -> {
                val binding: ItemActualExpenseBinding = DataBindingUtil.inflate(inflater,
                        R.layout.item_actual_expense, parent, false)
                ExpensesViewHolder(binding)
            }
        }
    }

    override fun getItemCount(): Int {
        return mTransactions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_INCOME -> (holder as IncomesViewHolder)
                    .bind(mTransactions[position] as Income)
            TYPE_EXPENSE -> {
                (holder as ExpensesViewHolder)
                        .bind(mTransactions[position] as Expense)
                holder.binding.ivCategoryColor.circleColor =
                        (mTransactions[position] as Expense).category?.color!!
            }
        }
        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
    }

    fun setOnClickListener(onClickListener: (Int, Int) -> Unit) {
        mClickListener = onClickListener
    }

    inner class IncomesViewHolder(val binding: ItemIncomeBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(income: Income) {
            binding.income = income
            binding.executePendingBindings()
        }

        override fun onClick(view: View?) {
            mClickListener((mTransactions[adapterPosition] as Income).incomeId, TYPE_INCOME)
        }
    }

    inner class ExpensesViewHolder(var binding: ItemActualExpenseBinding) :
            RecyclerView.ViewHolder(binding.root), View.OnClickListener {

        init {
            binding.root.setOnClickListener(this)
        }

        fun bind(expense: Expense) {
            binding.expense = expense
            binding.executePendingBindings()
        }

        override fun onClick(p0: View?) {
            mClickListener((mTransactions[adapterPosition] as Expense).expenseId, TYPE_EXPENSE)
        }
    }

}