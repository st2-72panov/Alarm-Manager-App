package com.example.alarmmanagerapp.ui.shared_compose_functions

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.sp
import com.example.alarmmanagerapp.ui.AppColor

@Composable
fun TopBarTitle(text: String, modifier: Modifier = Modifier) = Text(
    modifier = modifier,
    text = text,
    textAlign = TextAlign.Center,
    color = AppColor.light,
    fontSize = 18.sp
)
