package com.anti_toxic.dota.databases.items

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items")
data class ItemEntity(
    @PrimaryKey
    @ColumnInfo(name = "itemId")
    val itemId: Int,
    @ColumnInfo(name = "dname")
    val name: String,
    @ColumnInfo(name = "cost")
    val cost: Int,
    @ColumnInfo(name = "hint")
    val hints: ArrayList<String>,
    @ColumnInfo(name = "cd")
    val cooldown: Int?,
    @ColumnInfo(name = "lore")
    val lore: String,
    @ColumnInfo(name = "img")
    val imageUrl: String
)