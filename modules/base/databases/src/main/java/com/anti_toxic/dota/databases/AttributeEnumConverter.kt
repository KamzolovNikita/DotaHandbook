package com.anti_toxic.dota.databases

import androidx.room.TypeConverter
import com.anti_toxic.dota.core_api.Attribute

class AttributeEnumConverter {

    @TypeConverter
    fun toAttribute(value: String) = enumValueOf<Attribute>(value)

    @TypeConverter
    fun fromAttribute(value: Attribute) = value.name
}