package com.anti_toxic.dota.databases.teams

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.anti_toxic.dota.core_api.Side
import com.anti_toxic.dota.databases.SideEnumTypeConverter

@Entity(tableName = "matches")
data class MatchEntity(
    @ColumnInfo(name = "match_id")
    val matchId: Long,
    @ColumnInfo(name = "duration")
    val duration: Long,
    @ColumnInfo(name = "start_time")
    val startTime: Long,
    @ColumnInfo(name = "opposing_team_name")
    val opposingTeamName: String,
    @ColumnInfo(name = "opposing_team_logo_url")
    val opposingTeamLogoUrl: String?,
    @ColumnInfo(name = "is_win")
    val isWin: Boolean,
    @ColumnInfo(name = "side")
    @TypeConverters(SideEnumTypeConverter::class)
    val side: Side,
    @ColumnInfo(name = "league_name")
    val leagueName: String?,
) {
    @PrimaryKey
    @ColumnInfo(name = "match_key")
    var matchKey: String = "$matchId.$side"
        set(value) {
            field = "$matchId.$side"
        }
}