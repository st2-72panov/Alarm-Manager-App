package com.example.alarmmanagerapp

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context

class AppClass: Application() {
    override fun onCreate() {
        super.onCreate()

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        val foregroundServiceChannel = NotificationChannel(
            AlarmService.CHANNEL_ID,
            "Alarm clock",
            NotificationManager.IMPORTANCE_HIGH
        )
        foregroundServiceChannel.description = "Alarm clock"
        notificationManager.createNotificationChannel(foregroundServiceChannel)
    }
}