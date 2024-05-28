package com.example.alarmmanagerapp.alarm_manager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import java.time.LocalDateTime

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        val period = intent.getIntExtra("period", 0)
        val title = intent.getStringExtra("title")
        val groupID = intent.getIntExtra("groupID", 0)

        // TODO: call alarm and dismiss current if playing

        if (period <= 0L) return
        AlarmScheduler(context!!).schedule(
            AlarmItem(
                LocalDateTime.now()
                    .withSecond(0)
                    .plusMinutes(period.toLong()),
                period,
                title,
                groupID
            )
        )
    }
}
