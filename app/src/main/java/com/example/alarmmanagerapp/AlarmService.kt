package com.example.alarmmanagerapp

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.media.Ringtone
import android.media.RingtoneManager
import android.os.IBinder
import androidx.core.app.NotificationCompat
import java.time.LocalTime

class AlarmService : Service() {
    private var ringtone: Ringtone? = null

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent ?: throw Error("Empty service intent")

        when (intent.action) {
            Action.Start.name -> start(
                intent.getStringExtra("title"),
                intent.getStringExtra("weekDays")?: "Однократно"
            )
            Action.Dismiss.name -> {
                ringtone?.stop()
                stopForeground(STOP_FOREGROUND_REMOVE)
                stopSelf()
            }
        }

        return super.onStartCommand(intent, flags, startId)
    }

    private fun start(title: String?, weekDays: String) {
        val ringtoneManager = RingtoneManager(this).also {
            it.setType(RingtoneManager.TYPE_ALARM)
        }
        val cursor = ringtoneManager.cursor.also { it.moveToFirst() }
        val ringtoneUri = ringtoneManager.getRingtoneUri(cursor.position)
        ringtone = RingtoneManager.getRingtone(this, ringtoneUri).also {
            it.isLooping = true
        }

        val intent = Intent(this, AlarmService::class.java).also {
            it.setAction(Action.Dismiss.name)
        }
        val pendingIntent = PendingIntent.getService(
            this, 1, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val time = LocalTime.now().let { "${it.hour}:${it.minute}" }
        val notification =
            NotificationCompat.Builder(this, CHANNEL_ID)
                .setContentTitle("Будильник ($weekDays)")
                .setContentText("($time) $title")
                .setSmallIcon(R.drawable.ac_solo_outlined_24)
                .setOngoing(true)
                .addAction(1, "Dismiss", pendingIntent).build()

        startForeground(
            NOTIFICATION_ID, notification
        )
        ringtone?.play()
    }

    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    companion object {
        const val CHANNEL_ID = "ALARM_SERVICE"
        const val NOTIFICATION_ID = 1

        enum class Action {
            Start, Dismiss
        }
    }
}
