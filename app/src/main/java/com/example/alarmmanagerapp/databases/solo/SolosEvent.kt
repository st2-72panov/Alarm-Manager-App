package com.example.alarmmanagerapp.databases.solo

import android.content.Context

sealed interface SolosEvent {
    data class SortDB(val sortType: SortType) : SolosEvent

    data class EnterEditDialog(val entity: SoloAlarmEntity?) : SolosEvent
    data object DismissEditDialog : SolosEvent
    data class ConfirmChanges(val context: Context, val updatedEntity: SoloAlarmEntity) : SolosEvent

    data object EnterSelectView : SolosEvent
    data object ExitSelectView : SolosEvent
    data class SelectEntity(val entity: SoloAlarmEntity) : SolosEvent
    data class UnselectEntity(val entity: SoloAlarmEntity) : SolosEvent
    data class DeleteEntities(val context: Context) : SolosEvent

    data class SetOn(val context: Context, val entity: SoloAlarmEntity, val isOn: Boolean) : SolosEvent
}

enum class SortType {
    Time, IsOn
}
