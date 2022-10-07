package com.anti_toxic.dota.databases

import androidx.room.TypeConverter
import com.anti_toxic.dota.core_api.Role
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class RoleListConverter {

    @TypeConverter
    fun toRoleList(value: String): ArrayList<Role> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun fromRoleList(value: ArrayList<Role>): String {
        return Json.encodeToString(value)
    }
}