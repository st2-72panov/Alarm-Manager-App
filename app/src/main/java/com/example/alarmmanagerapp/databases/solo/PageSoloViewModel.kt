package com.example.alarmmanagerapp.databases.solo
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi

@OptIn(ExperimentalCoroutinesApi::class)
class PageSoloViewModel(
    private val dao: SolosDao
) : ViewModel() {
//    private val _state = MutableStateFlow(PageSoloState())
//    private val _sortType = MutableStateFlow(SortType.Time)
//    private val _alarms =
//        _sortType.flatMapLatest { sortType ->
//            when(sortType) {
//                SortType.Time -> dao.getEntitiesByTime()
//                SortType.IsOn -> dao.getEntitiesByIsOn()
//            }
//        }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), emptyList())
//
//    val state = combine(_state, _sortType, _alarms) { state, sortType, alarms ->
//        state.copy(
//            sortType = sortType,
//            alarms = alarms
//        )
//    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(), PageSoloState())
//
//
//    fun onEvent(event: SolosEvent) {
//        when (event) {
//            SolosEvent.DeleteEntities -> TODO()
//            SolosEvent.EnterSelectView -> TODO()
//            SolosEvent.ExitSelectView -> TODO()
//            SolosEvent.HideAddingDialog -> TODO()
//            is SolosEvent.HideEditingDialog -> TODO()
//            SolosEvent.SaveEntity -> TODO()
//            is SolosEvent.SelectEntity -> TODO()
//            is SolosEvent.SetOn -> TODO()
//            is SolosEvent.SetTime -> TODO()
//            is SolosEvent.SetTitle -> TODO()
//            is SolosEvent.SetWeekDays -> TODO()
//            SolosEvent.ShowAddingDialog -> TODO()
//            is SolosEvent.ShowEditingDialog -> TODO()
//            is SolosEvent.SortDB -> TODO()
//            is SolosEvent.UnselectEntity -> TODO()
//        }
//    }
}