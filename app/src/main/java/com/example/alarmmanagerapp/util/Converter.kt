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
        return weekDays.toInt()
    }
    @TypeConverter
    fun toWeekDays(weekDaysInt: Int): WeekDays {
        return getWeekDays(weekDaysInt)
    }

    @TypeConverter
    fun fromTime(time: LocalTime): Int {
        return time.toSecondOfDay()
    }
    @TypeConverter
    fun toTime(time: Int): LocalTime {
        return LocalTime.ofSecondOfDay(time.toLong())
    }

    companion object {
        fun WeekDays.toInt(): Int {
            var result = 0
            for (weekDay in this) {
                result += 1 shl (6 - weekDay.ordinal)
            }
            return result
        }
        fun getWeekDays(weekDaysInt: Int): WeekDays {
            var weekDays = weekDaysInt
            val result = WeekDays()
            for (i in 0..6) {
                if (weekDays % 2 == 1)
                    result.add(DayOfWeek.of(7 - i))
                weekDays /= 2
            }
            return result
        }

        fun WeekDays.toStringEnumeration(): String {
            return when (this.size) {
                0 -> "Однократно"
                7 -> "Ежедневно"
                else -> this.let {
                    val result = it.joinToString(" ") { dayOfWeek ->
                        dayOfWeek.toRussianAbbrev()
                    }
                    when (result) {
                        "пн вт ср чт пт" -> "По будням"
                        "сб вс" -> "По выходным"
                        else -> result
                    }
                }
            }
        }

        fun DayOfWeek.toRussianAbbrev(): String {
            return when (this) {
                DayOfWeek.MONDAY -> "пн"
                DayOfWeek.TUESDAY -> "вт"
                DayOfWeek.WEDNESDAY -> "ср"
                DayOfWeek.THURSDAY -> "чт"
                DayOfWeek.FRIDAY -> "пт"
                DayOfWeek.SATURDAY -> "сб"
                DayOfWeek.SUNDAY -> "вс"
            }
        }
    }
}