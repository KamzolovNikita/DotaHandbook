package com.anti_toxic.dota.databases

import androidx.room.TypeConverter
import com.anti_toxic.dota.core_api.Side

class SideEnumTypeConverter {

    @TypeConverter
    fun toSide(value: String) = enumValueOf<Side>(value)

    @TypeConverter
    fun fromHealth(value: Side) = value.name
}