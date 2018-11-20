package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import rmnvich.apps.familybudget.data.common.Constants.DEFAULT_COLOR

@Entity
class Category {

    @PrimaryKey(autoGenerate = true)
    var categoryId: Int = 0

    var name: String = ""
    var color: Int = DEFAULT_COLOR
}