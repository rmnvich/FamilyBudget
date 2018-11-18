package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Expense(@PrimaryKey(autoGenerate = true)
                   var expenseId: Int,
                   var value: String,
                   var comment: String,
                   var userId: Int,
                   var userName: String,
                   var userRelationshipType: String,
                   var categoryId: Int,
                   var timestamp: Long,
                   var isPlannedExpense: Boolean) {

    var plannedUntil: Long? = 0L
}