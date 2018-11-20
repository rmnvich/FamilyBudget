package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Expense {

    @PrimaryKey(autoGenerate = true)
    var expenseId: Int = 0

    var value: String = "0"
    var comment: String = ""
    var userName: String = ""
    var userRelationship: String = ""

    @Embedded
    var category: Category? = null
    var timestamp: Long = 0L
    var isPlannedExpense: Boolean = false
}