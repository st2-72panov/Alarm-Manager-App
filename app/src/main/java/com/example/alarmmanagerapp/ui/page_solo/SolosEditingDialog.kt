package com.example.alarmmanagerapp.ui.page_solo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.alarmmanagerapp.databases.solo.SoloAlarmEntity
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.ui.shared_compose_functions.CircularTimeLists
import com.example.alarmmanagerapp.ui.shared_compose_functions.TopBarTitle
import com.example.alarmmanagerapp.ui.shared_compose_functions.WeekDaysCheckField

@Composable
fun SolosEditingDialog(
    alarmEntity: SoloAlarmEntity, onSave: (SoloAlarmEntity) -> Unit, onCancel: () -> Unit
) = Dialog(onDismissRequest = { /*do nothing*/ }) {

    var editingEntity by remember {
        mutableStateOf(alarmEntity)
    }
    val interactionSource = remember { MutableInteractionSource() }
    val focusManager = LocalFocusManager.current

    Card(
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(AppColor.dimmest2),
        modifier = Modifier.clickable (
            interactionSource = interactionSource,
            indication = null
        ) {
            focusManager.clearFocus()
        }
    ) {

        Column(
            Modifier.padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            TopBarTitle(
                if (editingEntity.id == null) "Новый будильник"
                else "Изменение будильника",
                Modifier.fillMaxWidth().padding(0.dp, 10.dp, 0.dp, 15.dp)
            )

            // Content
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // week days + text field
                Column(
                    Modifier.width(50.dp * 3),
                ){
                    WeekDaysCheckField(
                        editingEntity.weekDays
                    ) { weekDays ->
                        editingEntity = editingEntity.copy(weekDays = weekDays)
                    }

                    Spacer(Modifier.height(7.dp))

                    OutlinedTextField(
                        shape = RoundedCornerShape(16.dp),
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedContainerColor = AppColor.dimmest,
                            unfocusedContainerColor = AppColor.dimmest,
                            focusedTextColor = AppColor.light,
                            unfocusedTextColor = AppColor.dim,
                            cursorColor = AppColor.contrast,
                            focusedBorderColor = AppColor.contrast
                        ),
                        maxLines = 3,
                        value = editingEntity.title,
                        onValueChange = { editingEntity = editingEntity.copy(title = it) },
                        placeholder = { Text("Title") }
                    )
                }

                Spacer(Modifier.width(5.dp))

                CircularTimeLists(
                    initialTime = editingEntity.time,
                    onHourChanged = { hour ->
                        editingEntity =
                            editingEntity.copy(time = editingEntity.time.withHour(hour))
                    },
                    onMinuteChanged = { minute ->
                        editingEntity =
                            editingEntity.copy(time = editingEntity.time.withMinute(minute))
                    }
                )
            }

            // Buttons
            Row(Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
                TextButton(onClick = { onCancel() }) {
                    Text("Отмена", color = AppColor.light)
                }
                TextButton(onClick = { onSave(editingEntity) }) {
                    Text("Сохранить", color = AppColor.light)
                }
            }
        }
    }
}
