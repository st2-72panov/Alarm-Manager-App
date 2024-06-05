package com.example.alarmmanagerapp.databases.solo

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.alarmmanagerapp.alarm_manager.AlarmItem
import com.example.alarmmanagerapp.alarm_manager.AlarmScheduler
import com.example.alarmmanagerapp.util.SoloAlarms
import com.example.alarmmanagerapp.util.WeekDays
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime

@OptIn(ExperimentalCoroutinesApi::class)
class PageSoloViewModel(
    private val dao: SolosDao
) : ViewModel() {

    private val _sortType = MutableStateFlow(SortType.Time)
    private val _alarms = _sortType.flatMapLatest { sortType ->
        when (sortType) {
            SortType.Time -> dao.getEntitiesByTime()
            SortType.IsOn -> dao.getEntitiesByIsOn()
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
    private val _selectedAlarms = MutableStateFlow(SoloAlarms())
    private val _inSelectView = MutableStateFlow(false)
    private val _inDialog = MutableStateFlow(false)

    val pageState = combine(
        _alarms, _sortType, _selectedAlarms, _inSelectView, _inDialog
    ) { alarms, sortType, selectedAlarms, inSelectView, inDialog ->
        PageSoloState(
            alarms, sortType, selectedAlarms, inSelectView, inDialog
        )
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PageSoloState())

    private var editingEntity: SoloAlarmEntity? = null

    fun onEvent(event: SolosEvent) {
        when (event) {
            is SolosEvent.SortDB -> {
                _sortType.update { event.sortType }
            }

            SolosEvent.EnterSelectView -> _inSelectView.update { true }

            SolosEvent.ExitSelectView -> {
                _inSelectView.update { false }
                _selectedAlarms.update { SoloAlarms() }
            }

            is SolosEvent.SelectEntity -> _selectedAlarms.update {
                it.add(event.alarm)
                it
            }

            is SolosEvent.UnselectEntity -> _selectedAlarms.update {
                it.remove(event.alarm)
                it
            }

            is SolosEvent.DeleteEntities -> {
                val alarmScheduler = AlarmScheduler(event.context)
                for (entity in _selectedAlarms.value) {
                    alarmScheduler.cancel(
                        AlarmItem(
                            AlarmItem.makeCombinedID(entity.id!!, 0.toShort()),
                            entity.title,
                            entity.time,
                            entity.weekDays
                        )
                    )
                    viewModelScope.launch {
                        dao.deleteEntity(entity)
                    }
                }
                onEvent(SolosEvent.ExitSelectView)
            }

            is SolosEvent.ShowEditingDialog -> {
                editingEntity =
                    event.alarm ?: SoloAlarmEntity(LocalTime.now(), WeekDays(), "", true)
                _inDialog.update { true }
            }

            is SolosEvent.HideEditingDialog -> {
                _inDialog.update { false }
                editingEntity = null
            }

            is SolosEvent.SetOn -> editingEntity = editingEntity?.copy(isOn = event.isOn)

            is SolosEvent.SetTime -> editingEntity = editingEntity?.copy(time = event.time)

            is SolosEvent.SetTitle -> editingEntity = editingEntity?.copy(title = event.title)

            is SolosEvent.SetWeekDays -> editingEntity =
                editingEntity?.copy(weekDays = event.weekDays)

            is SolosEvent.SaveEntity -> {
                viewModelScope.launch {
                    val entity = editingEntity?.copy()!!
                    dao.insertEntity(entity)

                    _alarms.value.find { item ->
                        item.copy(id = entity.id) == entity
                    }?.let {
                        AlarmScheduler(event.context).schedule(
                            AlarmItem(
                                AlarmItem.makeCombinedID(it.id!!, 0), it.title, it.time, it.weekDays
                            )
                        )
                    }
                }
                onEvent(SolosEvent.HideEditingDialog)
            }
        }
    }

    private fun getAlarmEntityCopy(id: Short): SoloAlarmEntity {
        return _alarms.value.find { item ->
            item.id == id
        }?.copy() ?: throw NoSuchElementException("There is no SoloAlarmEntity with such id")
    }
}