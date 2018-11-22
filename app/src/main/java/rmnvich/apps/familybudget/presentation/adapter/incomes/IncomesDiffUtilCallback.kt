package rmnvich.apps.familybudget.presentation.adapter.incomes

import android.support.v7.util.DiffUtil
import rmnvich.apps.familybudget.data.entity.Income

class IncomesDiffUtilCallback(private val oldList: List<Income>,
                              private var newList: List<Income>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldIncome = oldList[oldItemPosition]
        val newIncome = newList[newItemPosition]
        return oldIncome.incomeId == newIncome.incomeId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldIncome = oldList[oldItemPosition]
        val newIncome = newList[newItemPosition]
        return oldIncome.value == newIncome.value &&
                oldIncome.userRelationshipType == newIncome.userRelationshipType &&
                oldIncome.userName == newIncome.userName &&
                oldIncome.timestamp == newIncome.timestamp &&
                oldIncome.comment == newIncome.comment &&
                oldIncome.incomeTypeId == newIncome.incomeTypeId
    }
}