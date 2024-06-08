package com.example.alarmmanagerapp.databases.solo

import com.example.alarmmanagerapp.util.SoloAlarms

data class PageSoloState(
    val sortType: SortType = SortType.Time,
    val inSelectView: Boolean = false,
    val inEditingDialog: Boolean = false,
    val alarms: List<SoloAlarmEntity> = emptyList(),
    val selectedAlarms: SoloAlarms? = null,
    val editingAlarm: SoloAlarmEntity? = null
)