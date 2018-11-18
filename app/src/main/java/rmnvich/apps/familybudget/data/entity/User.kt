package rmnvich.apps.familybudget.data.entity

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.arch.persistence.room.TypeConverters
import rmnvich.apps.familybudget.domain.RoomConverter

@Entity
@TypeConverters(RoomConverter::class)
class User {

    @PrimaryKey(autoGenerate = true)
    var userId: Int = 0

    var name: String = ""
    var lastname: String = ""
    var password: String = ""
    var photoPath: String = ""
    var relationship: String = ""
    var incomeTypeIds: List<Int> = emptyList()
}