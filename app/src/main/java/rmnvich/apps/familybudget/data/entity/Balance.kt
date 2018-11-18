package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Balance(@PrimaryKey(autoGenerate = true)
                   var id: Int,
                   var balance: String,
                   var totalActualExpenses: String,
                   var totalPlannedExpenses: String)