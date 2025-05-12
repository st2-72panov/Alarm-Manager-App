package com.example.alarmmanagerapp.alarm_manager

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.room.Room
import com.example.alarmmanagerapp.alarm_display.AlarmScreen
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel
import com.example.alarmmanagerapp.databases.solo.SolosDB
import com.example.alarmmanagerapp.util.Converter.Companion.getWeekDays
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return

        val title = intent.getStringExtra("title")

        val alarmScreenIntent = Intent(context, AlarmScreen::class.java).also {
            it.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP)
        }
        val pendingIntent = PendingIntent.getActivity(
            context,
            1,
            alarmScreenIntent,
            PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context!!, NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(android.R.drawable.ic_lock_idle_alarm)
            .setContentTitle("Alarm clock")
            .setContentText(title)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setCategory(NotificationCompat.CATEGORY_ALARM)
            .setFullScreenIntent(pendingIntent, true)

        val notificationManager = NotificationManagerCompat.from(context)
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.POST_NOTIFICATIONS
            ) != PackageManager.PERMISSION_GRANTED
        ) return
        notificationManager.notify(NOTIFICATION_ID, builder.build())

        runBlocking {
            val combinedID = intent.getIntExtra("combinedID", -1)
            val time = LocalTime.ofSecondOfDay(intent.getIntExtra("time", -1).toLong())
            val weekDays = getWeekDays(intent.getIntExtra("weekDays", -1))
            if (!weekDays.isEmpty()) {
                AlarmScheduler(context).schedule(
                    AlarmItem(combinedID, title, time, weekDays)
                )
                return@runBlocking
            }

            val groupID = AlarmItem.retrieveGroupID(combinedID)
            val internalID = AlarmItem.retrieveInternalID(combinedID)
            if (groupID == PageSoloViewModel.GROUP_ID) {
                val solosDB by lazy {
                    Room.databaseBuilder(
                        context,
                        SolosDB::class.java,
                        SolosDB.NAME
                    ).fallbackToDestructiveMigration().build()
                }
                solosDB.dao.deleteEntity(internalID)
            }
        }
    }

    companion object {
        const val NOTIFICATION_CHANNEL_ID = "ALARM"
        const val NOTIFICATION_ID = 1
    }
}
