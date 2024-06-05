package com.example.alarmmanagerapp.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp

@Composable
fun TopBarTitle(text: String) = Text(
    text = text,
    textAlign = TextAlign.Center,
    color = AppColor.light,
    fontSize = 18.sp
)
