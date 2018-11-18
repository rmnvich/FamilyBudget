package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Balance(var balance: String) {

    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

    var totalActualExpenses: String = "0"
    var totalPlannedExpenses: String = "0"
}