package com.example.alarmmanagerapp.ui.page_solo

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.alarmmanagerapp.databases.solo.PageSoloState
import com.example.alarmmanagerapp.databases.solo.SoloAlarmEntity
import com.example.alarmmanagerapp.databases.solo.SolosEvent
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.ui.shared_functions.AppSwitch
import com.example.alarmmanagerapp.ui.shared_functions.RoundCheckIcon
import com.example.alarmmanagerapp.util.fromWeekDaysToString
import kotlinx.coroutines.flow.StateFlow

@Composable
fun SingleAlarmClock(
    pageStateFlow: StateFlow<PageSoloState>,
    onEvent: (SolosEvent) -> Unit,
    alarmEntity: SoloAlarmEntity
) {
    val haptic = LocalHapticFeedback.current
    val pageState by pageStateFlow.collectAsState()
    val isSelected = pageState.selectedAlarms.contains(alarmEntity)

    val containerTargetColor by animateColorAsState(
        targetValue = if (isSelected) AppColor.dimmest1 else AppColor.dimmest2,
        animationSpec = tween(200), ""
    )

    Card(
        colors = CardDefaults.cardColors(
            containerColor = containerTargetColor
        ),
        modifier = Modifier
            .fillMaxWidth()
            .height(120.dp)
            .padding(10.dp)
            .pointerInput(true) {
                detectTapGestures(
                    onLongPress = {
                        haptic.performHapticFeedback(HapticFeedbackType.LongPress)
                        onEvent(SolosEvent.EnterSelectView)
                    },
                    onPress = {
                        if (pageState.inSelectView)
                            onEvent(SolosEvent.SelectEntity(alarmEntity))
                        else
                            onEvent(SolosEvent.ShowEditingDialog(alarmEntity))
                    }
                )
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(14.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            val timeTextSize = 32.sp
            val additionalTextSize = 14.sp
            val timeColor = if (alarmEntity.isOn) AppColor.light else AppColor.dim
            val faintColor = if (alarmEntity.isOn) AppColor.dim else AppColor.dimmest

            // left frame
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                // time + title
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    Text(
                        alarmEntity.time
                            .withSecond(0)
                            .withNano(0)
                            .toString(),
                        fontSize = timeTextSize,
                        color = timeColor
                    )
                    Spacer(Modifier.width(7.dp))
                    Text(
                        alarmEntity.title,
                        modifier = Modifier
                            .width(180.dp)
                            .offset(0.dp, (-4).dp),
                        fontSize = additionalTextSize,
                        color = faintColor,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 2
                    )
                }
                // weekdays
                Text(
                    text = fromWeekDaysToString(alarmEntity.weekDays),
                    fontSize = additionalTextSize,
                    color = faintColor
                )
            }

            // right button
            if (pageState.inSelectView)
                RoundCheckIcon(isSelected)
            else
                AppSwitch(haptic, alarmEntity.isOn, ) {
                    onEvent(
                        SolosEvent.SetOn(alarmEntity, !alarmEntity.isOn)
                    )
                }
        }
    }
}
