package com.knightshrestha.neonnovels.datasources.local.database

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.knightshrestha.lightnovels.localdatabase.helpers.AssociatedTitles
import com.knightshrestha.lightnovels.localdatabase.helpers.Count
import java.lang.reflect.Type

class ListConverters {
    @TypeConverter
    fun listToString(list: List<String>?): String? {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToList(string: String?): List<String>? {
        val listType: Type = object : TypeToken<List<String>>() {}.type
        return Gson().fromJson(string, listType)
    }
}

class CountConverters {
    @TypeConverter
    fun countToString(count: Count): String {
        return Gson().toJson(count)
    }

    @TypeConverter
    fun stringToCount(string: String?): Count {
        val listType: Type = object : TypeToken<Count>() {}.type
        return Gson().fromJson(string, listType)
    }
}

class RelatedTitleConverters {
    @TypeConverter
    fun relatedTitleToString(list: List<AssociatedTitles>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun stringToRelatedTitle(string: String?): List<AssociatedTitles> {
        val listType: Type = object : TypeToken<List<AssociatedTitles>>() {}.type
        return Gson().fromJson(string, listType)
    }
}