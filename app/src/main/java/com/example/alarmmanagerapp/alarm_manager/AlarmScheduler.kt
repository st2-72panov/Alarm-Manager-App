package com.example.alarmmanagerapp.alarm_manager

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmmanagerapp.util.Converter.Companion.toInt
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("ScheduleExactAlarm")
    fun schedule(item: AlarmItem) {
        // setting time of nearest alarm
        val time: LocalDateTime
        LocalDateTime.now().also {
            val nearestAlarm: DayOfWeek = let { _ ->
                val weekDays = item.weekDays.toMutableList()

                if (weekDays.isEmpty()) {  // then ^return ...
                    if (it.toLocalTime() < item.time) return@let it.dayOfWeek  // ... today
                    else return@let DayOfWeek.entries[(it.dayOfWeek.ordinal + 1) % 7]  // ... next day
                }

                weekDays.add(it.dayOfWeek)
                weekDays.sort()
                val nearestDayIndex = weekDays.indexOf(it.dayOfWeek) + 1

                // ^return one of days from if-statement
                if (weekDays[nearestDayIndex] == it.dayOfWeek && item.time <= it.toLocalTime()) {
                    if (nearestDayIndex == weekDays.size - 1)
                        weekDays[0]
                    else
                        weekDays[nearestDayIndex + 1]
                } else
                    weekDays[nearestDayIndex]
            }

            var delta = nearestAlarm.ordinal - it.dayOfWeek.ordinal
            if (delta < 0 || (delta == 0 && item.time <= it.toLocalTime()))
                delta += 7
            time = it
                .withSecond(0)
                .withNano(0)
                .withMinute(item.time.minute)
                .withHour(item.time.hour)
                .plusDays(delta.toLong())
        }

        val showIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(),  // Empty because it is unnecessary
            PendingIntent.FLAG_IMMUTABLE
        )
        val broadcastIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("combinedID", item.combinedID)
            putExtra("title", item.title)
            putExtra("time", item.time.toSecondOfDay())
            putExtra("weekDays", item.weekDays.toInt())
        }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                showIntent
            ),
            PendingIntent.getBroadcast(
                context,
                item.combinedID,
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.combinedID,
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}