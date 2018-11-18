package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Embedded
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
data class Income(@PrimaryKey(autoGenerate = true)
                  var incomeId: Int,
                  var value: String,
                  var userId: Int,
                  var userName: String,
                  var userRelationshipType: String,
                  @Embedded
                  var incomeType: IncomeType,
                  var timestamp: Long)