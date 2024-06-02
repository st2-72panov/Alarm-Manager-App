package com.example.alarmmanagerapp.databases.solo
import com.example.alarmmanagerapp.util.SoloAlarms

data class PageSoloState(
    val alarms: List<SoloAlarmEntity> = emptyList(),
    var sortType: SortType = SortType.Time,
    val selectedAlarms: SoloAlarms = SoloAlarms(),
    var inSelectView: Boolean = false,
    var inDialog: Boolean = false
)
