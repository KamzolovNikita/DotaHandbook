package com.anti_toxic.dota.databases.heroes

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.anti_toxic.dota.core_api.AttackType
import com.anti_toxic.dota.core_api.Attribute
import com.anti_toxic.dota.core_api.Role
import com.anti_toxic.dota.databases.AttackTypeEnumConverter
import com.anti_toxic.dota.databases.AttributeEnumConverter

@Entity(tableName = "heroes")
data class HeroEntity(
    @PrimaryKey
    @ColumnInfo(name = "localized_name")
    val name: String,
    @ColumnInfo(name = "primary_attr")
    @TypeConverters(AttributeEnumConverter::class)
    val primaryAttribute: Attribute,
    @ColumnInfo(name = "attack_type")
    @TypeConverters(AttackTypeEnumConverter::class)
    val attackType: AttackType,
    @ColumnInfo(name = "roles")
    val roles: ArrayList<Role>,
    @ColumnInfo(name = "img")
    val imageUrl: String
)