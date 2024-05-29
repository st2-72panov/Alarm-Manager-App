package com.example.alarmmanagerapp.databases.groups
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.TreeSet

@Entity(tableName = GroupsDB.DB_NAME)
data class GroupAlarmEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Short? = null,
    val title: String,
    val weekDays: TreeSet<DayOfWeek>,

    val time: LocalTime,
    val fromBeginning: Boolean,
    val quantity: Int,
    val period: Int,

    val isOn: Boolean
)