package com.example.alarmmanagerapp.ui.page_solo

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import com.example.alarmmanagerapp.R
import com.example.alarmmanagerapp.databases.solo.PageSoloState
import com.example.alarmmanagerapp.databases.solo.SoloAlarmEntity
import com.example.alarmmanagerapp.databases.solo.SolosEvent
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.ui.shared_compose_functions.DeletionDialog
import com.example.alarmmanagerapp.util.WeekDays
import kotlinx.coroutines.flow.StateFlow
import java.time.DayOfWeek
import java.time.LocalTime

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageSolo(
    pageStateFlow: StateFlow<PageSoloState>,
    onEvent: (SolosEvent) -> Unit,
) {
    val context = LocalContext.current
    val pageState by pageStateFlow.collectAsState()
    var inConfirmDeletionDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(floatingActionButton = {
        FloatingActionButton(
            onClick = {
                if (pageState.inSelectView) inConfirmDeletionDialog = true
                else onEvent(SolosEvent.EnterEditDialog(null))
            }, shape = CircleShape, containerColor = AppColor.addButton
        ) {
            Icon(
                ImageVector.vectorResource(
                    if (pageState.inSelectView) R.drawable.round_arrow_forward_24
                    else R.drawable.round_add_36
                ), null, tint = AppColor.contrast
            )
        }
    }, contentColor = AppColor.background) { _ ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.background
        ) {
            LazyColumn(Modifier.fillMaxSize()) {
                items(pageState.alarms.size) { index ->
                    val alarm = pageState.alarms[index]
                    SoloAlarmClock(pageStateFlow, onEvent, alarm, index)
                }
            }
        }

        if (inConfirmDeletionDialog) DeletionDialog(onDismissRequest = {
            inConfirmDeletionDialog = false
        }, onLeaveRequest = {
            inConfirmDeletionDialog = false
            onEvent(SolosEvent.ExitSelectView)
        }, onConfirmation = {
            inConfirmDeletionDialog = false
            onEvent(SolosEvent.DeleteEntities(context))
        })

        if (pageState.inEditingDialog) SolosEditingDialog(alarmEntity = pageState.editingAlarm!!,
            onSave = {
                onEvent(SolosEvent.ConfirmChanges(context, it))
            },
            onCancel = {
                onEvent(SolosEvent.DismissEditDialog)
            })
    }
}


private fun getTestSoloAlarmEntity(): SoloAlarmEntity {
    return SoloAlarmEntity(
        LocalTime.now(),
        WeekDays().apply { this.add(DayOfWeek.MONDAY) },
        "Hello!",
        false
    )
}
