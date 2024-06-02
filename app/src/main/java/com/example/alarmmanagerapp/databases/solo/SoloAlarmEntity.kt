package com.example.alarmmanagerapp.databases.solo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.TreeSet

@Entity(tableName = SolosDB.NAME)
data class SoloAlarmEntity(
    val time: LocalTime,
    val weekDays: TreeSet<DayOfWeek>,
    val title: String,
    val isOn: Boolean,
    @PrimaryKey(autoGenerate = true)
    val id: Short? = null
)