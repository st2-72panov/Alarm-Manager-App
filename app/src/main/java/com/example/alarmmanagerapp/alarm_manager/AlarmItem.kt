package com.example.alarmmanagerapp.alarm_manager

import java.time.LocalDateTime

data class AlarmItem (
    val time: LocalDateTime,
    val period: Int? = null,  // minutes
    val title: String? = null,
    val groupID: Int = 0
)