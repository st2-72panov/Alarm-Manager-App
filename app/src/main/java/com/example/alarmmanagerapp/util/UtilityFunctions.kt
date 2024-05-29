package com.example.alarmmanagerapp.util

import java.time.DayOfWeek
import java.util.TreeSet

typealias WeekDays = TreeSet<DayOfWeek>

fun WeekDays.compareTo(o2: WeekDays): Int {
    return  this.makeComparable() - o2.makeComparable()
}

private fun WeekDays.makeComparable(): Int {
    var result = 0
    for (weekDay in this) {
        result += 1 shl (7 - weekDay.ordinal)
    }
    return result
}
