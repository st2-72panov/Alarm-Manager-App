package com.example.alarmmanagerapp.databases.solo

import android.content.Context
import com.example.alarmmanagerapp.util.WeekDays
import java.time.LocalTime

sealed interface SolosEvent {
    data class SetTime(val alarm: SoloAlarmEntity, val time: LocalTime) : SolosEvent
    data class SetWeekDays(val alarm: SoloAlarmEntity, val weekDays: WeekDays) : SolosEvent
    data class SetTitle(val alarm: SoloAlarmEntity, val title: String) : SolosEvent
    data class SetOn(val alarm: SoloAlarmEntity, val isOn: Boolean) : SolosEvent

    data class ShowEditingDialog(val alarm: SoloAlarmEntity?) : SolosEvent
    data object HideEditingDialog : SolosEvent
    data class SaveEntity(val context: Context) : SolosEvent

    data object EnterSelectView : SolosEvent
    data object ExitSelectView : SolosEvent
    data class SelectEntity(val alarm: SoloAlarmEntity) : SolosEvent
    data class UnselectEntity(val alarm: SoloAlarmEntity) : SolosEvent
    data class DeleteEntities(val context: Context) : SolosEvent

    data class SortDB(val sortType: SortType) : SolosEvent
}

enum class SortType {
    Time, IsOn
}
