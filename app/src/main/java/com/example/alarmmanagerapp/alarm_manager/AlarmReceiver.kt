package com.example.alarmmanagerapp.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("title") ?: return
        val period = intent.getLongExtra("period", 0)

        // TODO: call alarm and dismiss current if playing

        if (period <= 0L) return
        AlarmScheduler(context!!).schedule(
            AlarmItem(
                title,
                LocalDateTime.now()
                    .withSecond(0)
                    .plusMinutes(period),
                period
            )
        )
    }
}
