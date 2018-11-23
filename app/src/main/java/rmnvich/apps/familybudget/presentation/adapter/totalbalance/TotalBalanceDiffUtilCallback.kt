package rmnvich.apps.familybudget.presentation.adapter.totalbalance

import android.support.v7.util.DiffUtil
import rmnvich.apps.familybudget.data.entity.Expense
import rmnvich.apps.familybudget.data.entity.Income
import java.lang.ClassCastException

class TotalBalanceDiffUtilCallback(private val oldList: List<Any>,
                                   private var newList: List<Any>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return try {
            if (oldItem is Income && newItem is Income)
                oldItem.incomeId == newItem.incomeId
            else (oldItem as Expense).expenseId == (newItem as Expense).expenseId
        } catch (e: ClassCastException) {
            false
        }
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldItem = oldList[oldItemPosition]
        val newItem = newList[newItemPosition]

        return try {
            if (oldItem is Income && newItem is Income)
                oldItem.value == newItem.value &&
                        oldItem.userRelationshipType == newItem.userRelationshipType &&
                        oldItem.userName == newItem.userName &&
                        oldItem.timestamp == newItem.timestamp &&
                        oldItem.comment == newItem.comment &&
                        oldItem.incomeType == newItem.incomeType
            else (oldItem as Expense).category?.name == (newItem as Expense).category?.name &&
                    oldItem.category?.color == newItem.category?.color &&
                    oldItem.isPlannedExpense == newItem.isPlannedExpense &&
                    oldItem.timestamp == newItem.timestamp &&
                    oldItem.comment == newItem.comment &&
                    oldItem.value == newItem.value
        } catch (e: ClassCastException) {
            false
        }
    }
}