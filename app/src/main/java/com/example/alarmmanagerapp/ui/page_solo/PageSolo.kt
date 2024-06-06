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
import com.example.alarmmanagerapp.databases.solo.SolosEvent
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.ui.shared_functions.DeletionDialog
import kotlinx.coroutines.flow.StateFlow

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun PageSolo(
    pageStateFlow: StateFlow<PageSoloState>,
    onEvent: (SolosEvent) -> Unit,
) {
    val context = LocalContext.current
    val pageState by pageStateFlow.collectAsState()
    var inDeleteDialog by remember {
        mutableStateOf(false)
    }

    Scaffold(
        floatingActionButton = {
            when (pageState.inSelectView) {
                false -> FloatingActionButton(
                    onClick = {
                        onEvent(SolosEvent.ShowEditingDialog(null))
                    },
                    shape = CircleShape,
                    containerColor = AppColor.addButton
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.round_add_36),
                        null,
                        tint = AppColor.contrast
                    )
                }

                else -> FloatingActionButton(
                    onClick = {
                        inDeleteDialog = true
                    },
                    shape = CircleShape,
                    containerColor = AppColor.addButton
                ) {
                    Icon(
                        ImageVector.vectorResource(R.drawable.round_arrow_forward_24),
                        null,
                        tint = AppColor.contrast
                    )
                }
            }
        }
    ) { _ ->
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = AppColor.background
        ) {
            LazyColumn {
//                items(pageState.alarms.size) { index ->
//                    SingleAlarmClock(pageState, onEvent, pageState.alarms[index])
//                }

//                item {
//                    SingleAlarmClock(
//                        pageStateFlow,
//                        onEvent,
//                        SoloAlarmEntity(
//                            LocalTime.now(),
//                            WeekDays().apply { this.add(DayOfWeek.MONDAY) },
//                            "Hello!",
//                            false
//                        )
//                    )
//                }
            }

            if (inDeleteDialog)
                DeletionDialog(
                    onDismissRequest = { inDeleteDialog = false },
                    onLeaveRequest = {
                        inDeleteDialog = false
                        onEvent(SolosEvent.ExitSelectView)
                    },
                    onConfirmation = {
                        inDeleteDialog = false
                        onEvent(SolosEvent.DeleteEntities(context))
                    }
                )
        }
    }
}

