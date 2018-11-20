package rmnvich.apps.familybudget.presentation.adapter.categories

import android.support.v7.util.DiffUtil
import android.util.Log.d
import rmnvich.apps.familybudget.data.entity.Category

class CategoriesDiffUtilCallback(private val oldList: List<Category>,
                                 private val newList: List<Category>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCategory = oldList[oldItemPosition]
        val newCategory = newList[newItemPosition]
        return oldCategory.categoryId == newCategory.categoryId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCategory = oldList[oldItemPosition]
        val newCategory = newList[newItemPosition]
        return oldCategory.name == newCategory.name &&
                oldCategory.color == newCategory.color
    }
}