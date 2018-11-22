package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity
class Income {

    @PrimaryKey(autoGenerate = true)
    var incomeId: Int = 0

    var value: String = ""
    var comment: String = ""
    var userName: String = ""
    var incomeType: String = ""
    var userRelationshipType: String = ""

    var timestamp: Long = 0L
}