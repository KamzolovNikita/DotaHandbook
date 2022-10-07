package com.anti_toxic.dota.databases

import androidx.room.TypeConverter
import com.anti_toxic.dota.core_api.AttackType

class AttackTypeEnumConverter {

    @TypeConverter
    fun toAttackType(value: String) = enumValueOf<AttackType>(value)

    @TypeConverter
    fun fromAttackType(value: AttackType) = value.name
}