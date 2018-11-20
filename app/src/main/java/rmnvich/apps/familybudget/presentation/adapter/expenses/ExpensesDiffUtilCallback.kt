package rmnvich.apps.familybudget.presentation.adapter.expenses

import android.support.v7.util.DiffUtil
import rmnvich.apps.familybudget.data.entity.Expense

class ExpensesDiffUtilCallback(private val oldList: List<Expense>,
                               private val newList: List<Expense>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldExpense = oldList[oldItemPosition]
        val newExpense = newList[newItemPosition]
        return oldExpense.expenseId == newExpense.expenseId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldExpense = oldList[oldItemPosition]
        val newExpense = newList[newItemPosition]
        return oldExpense.category?.name == newExpense.category?.name &&
                oldExpense.category?.color == newExpense.category?.color &&
                oldExpense.isPlannedExpense == newExpense.isPlannedExpense &&
                oldExpense.timestamp == newExpense.timestamp &&
                oldExpense.comment == newExpense.comment &&
                oldExpense.value == newExpense.value
    }
}