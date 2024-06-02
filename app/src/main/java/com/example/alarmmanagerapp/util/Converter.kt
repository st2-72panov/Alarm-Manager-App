package com.example.alarmmanagerapp.util
import androidx.room.TypeConverter
import com.example.alarmmanagerapp.databases.solo.SoloAlarmEntity
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.TreeSet

typealias SoloAlarms = TreeSet<SoloAlarmEntity>
typealias WeekDays = TreeSet<DayOfWeek>

class Converter {
    @TypeConverter
    fun fromWeekDays(weekDays: WeekDays): Int {
        var result = 0
        for (weekDay in weekDays) {
            result += 1 shl (7 - weekDay.ordinal)
        }
        return result
    }
    @TypeConverter
    fun toWeekDays(weekDaysInt: Int): WeekDays {
        var weekDays = weekDaysInt
        val result = WeekDays()
        for (i in 0..6) {
            if (weekDays % 2 == 1)
                result.add(DayOfWeek.of(7 - i))
            weekDays /= 2
        }
        return result
    }

    @TypeConverter
    fun fromTime(time: LocalTime): Int {
        return time.toSecondOfDay()
    }
    @TypeConverter
    fun toTime(time: Int): LocalTime {
        return LocalTime.ofSecondOfDay(time.toLong())
    }
}