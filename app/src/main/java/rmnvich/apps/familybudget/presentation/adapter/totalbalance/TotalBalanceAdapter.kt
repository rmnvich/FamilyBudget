package rmnvich.apps.familybudget.presentation.adapter.totalbalance

import android.databinding.DataBindingUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AlphaAnimation
import rmnvich.apps.familybudget.R
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import rmnvich.apps.familybudget.databinding.ItemActualExpenseBinding
import rmnvich.apps.familybudget.databinding.ItemIncomeBinding
import java.util.*

class TotalBalanceAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val TYPE_INCOME = 1
    private val TYPE_EXPENSE = 2

    private var transactions: List<Any> = LinkedList()

    fun setData(transactions: List<Any>) {
        this.transactions = transactions
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (transactions[position] is Income) {
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
        return transactions.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            TYPE_INCOME -> (holder as IncomesViewHolder)
                    .bind(transactions[position] as Income)
            TYPE_EXPENSE -> {
                (holder as ExpensesViewHolder)
                        .bind(transactions[position] as Expense)
                holder.binding.ivCategoryColor.circleColor =
                        (transactions[position] as Expense).category?.color!!
            }
        }
        setFadeAnimation(holder.itemView)
    }

    private fun setFadeAnimation(view: View) {
        val anim = AlphaAnimation(0.0f, 1.0f)
        anim.duration = 450
        view.startAnimation(anim)
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
//            mListener.onClick(mIncomesList[adapterPosition].incomeId)
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
//            mListener.onClick(mExpenseList[adapterPosition].expenseId)
        }
    }

}