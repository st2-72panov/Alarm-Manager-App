package com.example.alarmmanagerapp.ui

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import com.example.alarmmanagerapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PageSettings(navigateToContent: () -> Unit) {
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = { TopBarTitle("Настройки") },
                navigationIcon = {
                    IconButton(onClick = { navigateToContent() }) {
                        Icon(
                            ImageVector.vectorResource(R.drawable.baseline_arrow_back_24),
                            null,
                            tint = AppColor.dim
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = AppColor.background,
                    titleContentColor = AppColor.light
                )
            )
        }
    ) { paddingValues ->
        Surface(
            modifier = Modifier.padding(paddingValues).fillMaxSize(),
            color = AppColor.background
        ) {
            Text("SETTINGS SETTINGS SETTINGS SETTINGS SETTINGS SETTINGS", color = AppColor.light)
        }
    }
}