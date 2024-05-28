package com.example.alarmmanagerapp.alarm_manager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmmanagerapp.MainActivity
import java.time.LocalDateTime
import java.time.ZoneId

class AlarmScheduler(
    private val context: Context
) {
    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(combinedID: CombinedID) {
        // retrieve AlarmItem via ids from DB
    }

    fun schedule(item: AlarmItem) {
        val time: LocalDateTime
        LocalDateTime.now().let {
            val weekDays = item.weekDays.toMutableList()
            weekDays.add(weekDays[0])
            var i = 0
            while (true)
                if (it.dayOfWeek == weekDays[i++])
                    break

            var delta = weekDays[i].ordinal - it.dayOfWeek.ordinal
            if (delta <= 0)
                delta += 7
            time = it
                .withSecond(0)
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