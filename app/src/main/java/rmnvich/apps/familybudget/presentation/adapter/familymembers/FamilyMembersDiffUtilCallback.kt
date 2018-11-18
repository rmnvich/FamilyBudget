package rmnvich.apps.familybudget.presentation.adapter.familymembers

import android.support.v7.util.DiffUtil
import rmnvich.apps.familybudget.data.entity.User

class FamilyMembersDiffUtilCallback(private val oldList: List<User>,
                                    private val newList: List<User>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldUser = oldList[oldItemPosition]
        val newUser = newList[newItemPosition]
        return oldUser.userId == newUser.userId
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldMovie = oldList[oldItemPosition]
        val newMovie = newList[newItemPosition]
        return oldMovie.name == newMovie.name &&
                oldMovie.lastname == newMovie.lastname &&
                oldMovie.relationship == newMovie.relationship &&
                oldMovie.photoPath == newMovie.photoPath
    }
}