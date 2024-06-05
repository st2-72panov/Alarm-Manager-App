package com.example.alarmmanagerapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.alarmmanagerapp.databases.groups.PageGroupsViewModel

@Composable
fun PageGroups(viewModel: PageGroupsViewModel) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = AppColor.background
    ) {
        Text("GROUPS GROUPS GROUPS GROUPS GROUPS GROUPS", color = AppColor.light)
    }
}