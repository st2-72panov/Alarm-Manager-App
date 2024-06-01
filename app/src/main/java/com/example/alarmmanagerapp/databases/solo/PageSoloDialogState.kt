package com.example.alarmmanagerapp.databases.solo
import com.example.alarmmanagerapp.util.WeekDays
import java.time.LocalTime

data class PageSoloDialogState(
    var time: LocalTime? = null,
    val weekDays: WeekDays = WeekDays(),
    var title: String = ""
) {
    fun unsetValues() {
        time = null
        weekDays.clear()
        title = ""
    }
}
