package com.example.alarmmanagerapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel

@Composable
fun PageSolo(viewModel: PageSoloViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColor.background
    ) {
        Text("SOLO SOLO SOLO SOLO SOLO SOLO SOLO SOLO", color = AppColor.light)
    }
}
