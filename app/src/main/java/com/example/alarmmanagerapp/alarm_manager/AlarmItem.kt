package com.example.alarmmanagerapp.alarm_manager

import java.time.LocalDateTime

data class AlarmItem (
    val title: String,
    val time: LocalDateTime,
    val period: Long? = null  // minutes
)