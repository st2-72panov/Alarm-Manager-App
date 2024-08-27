package com.example.alarmmanagerapp.ui.shared_compose_functions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.util.WeekDays
import com.example.alarmmanagerapp.util.toRussianAbbrev
import java.time.DayOfWeek

@Composable
fun WeekDaysCheckField(
    initialState: WeekDays, onStateChange: (WeekDays) -> Unit
) {
    val weekDays = remember {
        mutableStateListOf<DayOfWeek>().apply { addAll(initialState) }
    }

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(AppColor.dimmest1)
    ) {
        Column {
            // first two
            for (i in 0..1)
                Row(verticalAlignment = Alignment.CenterVertically) {
                    for (j in 0..2) {
                        val day = DayOfWeek.entries[i * 3 + j]
                        WeekDayTextButton(day, weekDays.contains(day)) {
                            if (day !in weekDays)
                                weekDays.add(day)
                            else
                                weekDays.remove(day)
                            onStateChange(WeekDays(weekDays))
                        }
                    }
                }

            // last one
            Row(
                Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                val isBright = weekDays.size == 7
                val targetColor by animateColorAsState(
                    if (isBright) AppColor.contrast else AppColor.dim,
                    tween(200), ""
                )
                TextButton(
                    onClick = {
                        if (!isBright) weekDays.apply {
                            clear()
                            addAll(DayOfWeek.entries)
                        }
                        else weekDays.clear()
                        onStateChange(WeekDays(weekDays))
                    }, colors = ButtonDefaults.textButtonColors(AppColor.dimmest1)) {
                    Text("Ежедневно", color = targetColor, fontWeight = FontWeight.Bold)
                }

                val day = DayOfWeek.SUNDAY
                WeekDayTextButton(day, weekDays.contains(day)) {
                    if (day !in weekDays)
                        weekDays.add(day)
                    else
                        weekDays.remove(day)
                    onStateChange(WeekDays(weekDays))
                }
            }
        }
    }
}


@Composable
private fun WeekDayTextButton(
    day: DayOfWeek,
    isChosen: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = { onClick() },
        modifier = Modifier.width(50.dp)
    ) {
        val targetColor by animateColorAsState(
            if (isChosen) AppColor.lightest else AppColor.dim,
            tween(200), ""
        )
        Text(
            day.toRussianAbbrev(), color = targetColor,
            maxLines = 1
        )
    }
}