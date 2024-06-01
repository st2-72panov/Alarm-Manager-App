package com.example.alarmmanagerapp.databases.solo
import com.example.alarmmanagerapp.util.WeekDays
import java.time.LocalTime

sealed interface SolosEvent {
    data class SetTime(val id: Short, val time: LocalTime): SolosEvent
    data class SetWeekDays(val id: Short, val weekDays: WeekDays): SolosEvent
    data class SetTitle(val id: Short, val title: String): SolosEvent
    data class SetOn(val id: Short, val on: Boolean): SolosEvent

    data object ShowAddingDialog: SolosEvent
    data object HideAddingDialog: SolosEvent
    data class ShowEditingDialog(val id: Short): SolosEvent
    data class HideEditingDialog(val id: Short): SolosEvent
    data object SaveEntity: SolosEvent

    data object EnterSelectView: SolosEvent
    data object ExitSelectView: SolosEvent
    data class SelectEntity(val id: Short): SolosEvent
    data class UnselectEntity(val id: Short): SolosEvent
    data object DeleteEntities: SolosEvent

    data class SortDB(val sortType: SortType): SolosEvent
}

enum class SortType {
    Time,
    IsOn
}
