package com.example.alarmmanagerapp.ui.shared_compose_functions

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.requiredSize
import androidx.compose.foundation.shape.RoundedCornerShape
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
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            for (day in DayOfWeek.entries.reversed())
                TextButton(
                    modifier = Modifier.requiredSize(50.dp, 35.dp),
                    onClick = {
                        if (day !in weekDays)
                            weekDays.add(day)
                        else
                            weekDays.remove(day)
                        onStateChange(WeekDays(weekDays))
                    }
                ) {
                    val targetColor by animateColorAsState(
                        if (weekDays.contains(day)) AppColor.lightest else AppColor.dim,
                        tween(200),
                        ""
                    )
                    Text(
                        day.toRussianAbbrev(), color = targetColor,
                        maxLines = 1
                    )
                }
        }
    }
}