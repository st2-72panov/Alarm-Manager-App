package com.example.alarmmanagerapp.alarm_manager

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.room.Room
import com.example.alarmmanagerapp.AlarmService
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel
import com.example.alarmmanagerapp.databases.solo.SolosDB
import com.example.alarmmanagerapp.util.Converter.Companion.getWeekDays
import com.example.alarmmanagerapp.util.Converter.Companion.toStringEnumeration
import kotlinx.coroutines.runBlocking
import java.time.LocalTime

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        intent ?: return
        context ?: return

        val title = intent.getStringExtra("title")
        val weekDays = getWeekDays(intent.getIntExtra("weekDays", -1))

        val alarmServiceIntent = Intent(context, AlarmService::class.java).also {
            it.setAction(AlarmService.Companion.Action.Start.name)
            it.putExtra("title", title)
            it.putExtra("weekDays", weekDays.toStringEnumeration())
        }
        context.startService(alarmServiceIntent)

        runBlocking {
            val combinedID = intent.getIntExtra("combinedID", -1)
            val time = LocalTime.ofSecondOfDay(intent.getIntExtra("time", -1).toLong())

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
}
