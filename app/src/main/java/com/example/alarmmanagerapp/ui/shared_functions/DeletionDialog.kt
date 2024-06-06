package com.example.alarmmanagerapp.ui.shared_functions

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.example.alarmmanagerapp.ui.AppColor

@Composable
fun DeletionDialog(
    onDismissRequest: () -> Unit,
    onLeaveRequest: () -> Unit,
    onConfirmation: () -> Unit
) {
    Dialog(onDismissRequest = onDismissRequest) {
        Card(
            modifier = Modifier.padding(16.dp),
            shape = RoundedCornerShape(16.dp),
            colors = CardDefaults.cardColors(AppColor.dimmest2)
        ) {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    "Удалить выбранные объекты?",
                    fontSize = 16.sp,
                    modifier = Modifier
                        .padding(0.dp, 18.dp, 0.dp, 0.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    color = AppColor.light
                )
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center,
                ) {
                    TextButton(
                        onClick = { onLeaveRequest() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Выйти", color = AppColor.lightest)
                    }
                    TextButton(
                        onClick = { onConfirmation() },
                        modifier = Modifier.padding(8.dp)
                    ) {
                        Text("Удалить", color = AppColor.lightest)
                    }
                }
            }
        }
    }
}