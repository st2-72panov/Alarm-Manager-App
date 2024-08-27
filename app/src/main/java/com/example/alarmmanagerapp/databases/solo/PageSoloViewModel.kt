package com.example.alarmmanagerapp.databases.solo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmmanagerapp.alarm_manager.AlarmItem
import com.example.alarmmanagerapp.alarm_manager.AlarmScheduler
import com.example.alarmmanagerapp.alarm_manager.ID
import com.example.alarmmanagerapp.util.SoloAlarms
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@OptIn(ExperimentalCoroutinesApi::class)
class PageSoloViewModel(
    private val dao: SolosDao
) : ViewModel() {
    
    private val _sortType = MutableStateFlow(SortType.Time)
    private val _inSelectView = MutableStateFlow(false)
    private val _inEditingDialog = MutableStateFlow(false)

    private val _alarms = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.Time -> dao.getEntitiesByTime()
            SortType.IsOn -> dao.getEntitiesByIsOn()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _selectedAlarms = MutableStateFlow<SoloAlarms?>(SoloAlarms())
    private val _editingAlarm = MutableStateFlow<SoloAlarmEntity?>(SoloAlarmEntity())

    val pageState = combine(
        _sortType, _inSelectView, _inEditingDialog, _alarms, _selectedAlarms, _editingAlarm
    ) { args ->
        @Suppress("UNCHECKED_CAST")  // checked)
        PageSoloState(
            args[0] as SortType,
            args[1] as Boolean,
            args[2] as Boolean,
            args[3] as List<SoloAlarmEntity>,
            args[4] as SoloAlarms?,
            args[5] as SoloAlarmEntity?
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PageSoloState())


    fun onEvent(event: SolosEvent) {
        when (event) {

            is SolosEvent.SortDB -> {
                _sortType.update { event.sortType }
            }

            is SolosEvent.SetOn -> viewModelScope.launch {
                val alarmScheduler = AlarmScheduler(event.context)
                val entity = event.entity
                val isOn = event.isOn
                val alarmItem = AlarmItem(
                    AlarmItem.makeCombinedID(entity.id!!, GROUP_ID),
                    entity.title,
                    entity.time,
                    entity.weekDays
                )

                if (!isOn) alarmScheduler.cancel(alarmItem)
                dao.insertEntity(entity.copy(isOn = isOn))
                if (isOn) alarmScheduler.schedule(alarmItem)
            }

            // SelectView options
            /////////////////////
            SolosEvent.EnterSelectView -> {
                onEvent(SolosEvent.DismissEditDialog)
                _selectedAlarms.update { SoloAlarms() }
                _inSelectView.update { true }
            }

            SolosEvent.ExitSelectView -> {
                _inSelectView.update { false }
                _selectedAlarms.update { null }
            }

            is SolosEvent.SelectEntity -> _selectedAlarms.update {
                it?.add(event.entity) ?: throw IllegalAccessError()
                it
            }

            is SolosEvent.UnselectEntity -> _selectedAlarms.update {
                it?.remove(event.entity) ?: throw IllegalAccessError()
                it
            }

            is SolosEvent.DeleteEntities -> {
                val selectedAlarms = _selectedAlarms.value ?: throw IllegalAccessError()
                val alarmScheduler = AlarmScheduler(event.context)

                for (entity in selectedAlarms) {
                    alarmScheduler.cancel(
                        AlarmItem(
                            AlarmItem.makeCombinedID(entity.id!!, GROUP_ID),
                            entity.title,
                            entity.time,
                            entity.weekDays
                        )
                    )
                    viewModelScope.launch { dao.deleteEntity(entity) }
                }
                onEvent(SolosEvent.ExitSelectView)
            }

            // EditDialog options
            /////////////////////
            is SolosEvent.EnterEditDialog -> {
                onEvent(SolosEvent.ExitSelectView)
                _editingAlarm.update { event.entity ?: SoloAlarmEntity() }
                _inEditingDialog.update { true }
            }

            SolosEvent.DismissEditDialog -> {
                _inEditingDialog.update { false }
                _editingAlarm.update { null }
            }

            is SolosEvent.ConfirmChanges -> {
                val oldAlarm = _editingAlarm.value ?: throw IllegalAccessError()
                var updatedAlarm = event.updatedEntity
                val isNewAlarm = updatedAlarm.id == null
                val alarmScheduler = AlarmScheduler(event.context)

                onEvent(SolosEvent.DismissEditDialog)

                viewModelScope.launch {
                    // cancel old version -> replace with updated -> schedule updated
                    if (!isNewAlarm) alarmScheduler.cancel(
                        AlarmItem(
                            AlarmItem.makeCombinedID(oldAlarm.id!!, GROUP_ID),
                            oldAlarm.title,
                            oldAlarm.time,
                            oldAlarm.weekDays
                        )
                    )

                    dao.insertEntity(updatedAlarm)

                    delay(300)  // alarm somehow doesn't manage to be inserted without this delay
                    if (isNewAlarm)  // then find generated id
                        updatedAlarm = _alarms.value.find { item ->
                            item.copy(id = null) == updatedAlarm
                        } ?: throw InternalError("Unexpected error")
                    alarmScheduler.schedule(
                        AlarmItem(
                            AlarmItem.makeCombinedID(updatedAlarm.id!!, GROUP_ID),
                            updatedAlarm.title,
                            updatedAlarm.time,
                            updatedAlarm.weekDays
                        )
                    )
                }
            }
        }
    }

    private fun getAlarmEntityCopy(id: Short): SoloAlarmEntity {
        return _alarms.value.find { item ->
            item.id == id
        }?.copy() ?: throw NoSuchElementException("There is no SoloAlarmEntity with such id")
    }
    
    companion object {
        const val GROUP_ID: ID = 0
    }
}