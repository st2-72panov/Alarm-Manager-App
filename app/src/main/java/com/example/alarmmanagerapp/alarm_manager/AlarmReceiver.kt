package com.example.alarmmanagerapp.alarm_manager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.runBlocking
import java.time.LocalDateTime

class AlarmReceiver: BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return

        val title = intent.getStringExtra("title")
        // TODO: trigger the alarm

        runBlocking {
            val combinedID = intent.getIntExtra("combinedID", -1)
            AlarmScheduler(context!!).schedule(combinedID)
        }
    }
}
