package com.example.alarmmanagerapp.alarm_manager
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.example.alarmmanagerapp.MainActivity
import java.time.ZoneId

class AlarmScheduler(
    private val context: Context
) {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    fun schedule(item: AlarmItem) {
        val showIntent = PendingIntent.getActivity(
            context,
            0,
            Intent(context, MainActivity::class.java),  // TODO: an activity that allows user to change alarm
            PendingIntent.FLAG_IMMUTABLE
        )
        val broadcastIntent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("period", item.period)
            putExtra("title", item.title)
            putExtra("groupID", item.groupID)
        }

        alarmManager.setAlarmClock(
            AlarmManager.AlarmClockInfo(
                item.time.atZone(ZoneId.systemDefault()).toEpochSecond() * 1000,
                showIntent
            ),
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                broadcastIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    fun cancel(item: AlarmItem) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                item.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}