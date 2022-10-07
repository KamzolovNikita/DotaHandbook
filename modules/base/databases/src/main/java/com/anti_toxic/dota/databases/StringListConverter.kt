package com.anti_toxic.dota.databases

import androidx.room.TypeConverter
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class StringListConverter {

    @TypeConverter
    fun toList(value: String): ArrayList<String> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromList(value: ArrayList<String>): String {
        return Json.encodeToString(value)
    }
}