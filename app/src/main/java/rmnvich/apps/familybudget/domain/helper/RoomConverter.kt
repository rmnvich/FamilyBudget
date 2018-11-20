package rmnvich.apps.familybudget.domain.helper

import android.arch.persistence.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class RoomConverter {

    private val gson: Gson = Gson()

    @TypeConverter
    fun toListOfIds(data: String?): List<Int> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Int>>() {}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromListOfIds(ids: List<Int>): String {
        return gson.toJson(ids)
    }
}