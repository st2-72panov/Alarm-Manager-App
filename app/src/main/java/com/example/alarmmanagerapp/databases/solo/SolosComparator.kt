package com.example.alarmmanagerapp.databases.solo

import com.example.alarmmanagerapp.util.compareTo

class SolosComparator : Comparator<SoloAlarmEntity> {
    override fun compare(o1: SoloAlarmEntity, o2: SoloAlarmEntity): Int {
        val timeDif = o1.time.compareTo(o2.time)
        if (timeDif != 0) return timeDif

        val isOnDif = o1.isOn.compareTo(o2.isOn)
        if (isOnDif != 0) return isOnDif

        val weekDaysDif = o1.weekDays.compareTo(o2.weekDays)
        if (weekDaysDif != 0) return weekDaysDif

        return o1.title.compareTo(o2.title)
    }
}
