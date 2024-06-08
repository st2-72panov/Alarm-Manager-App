package com.example.alarmmanagerapp.databases.solo

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.alarmmanagerapp.util.WeekDays
import java.time.LocalTime

@Entity(tableName = SolosDB.NAME)
data class SoloAlarmEntity(
    val time: LocalTime = LocalTime.now().withSecond(0).withNano(0),
    val weekDays: WeekDays = WeekDays(),
    val title: String = "",
    val isOn: Boolean = true,
    @PrimaryKey(autoGenerate = true) val id: Short? = null
) : Comparable<SoloAlarmEntity> {
    override fun compareTo(other: SoloAlarmEntity): Int {
        return this.id?.compareTo(other.id!!)!!
    }

    override fun toString(): String {
        return "$time, $weekDays, '$title', $isOn, $id"
    }

}