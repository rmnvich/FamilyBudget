package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class IncomeType(@PrimaryKey(autoGenerate = true)
                      var id: Int,
                      var name: String)