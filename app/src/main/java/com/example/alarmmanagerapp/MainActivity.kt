package com.example.alarmmanagerapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.alarmmanagerapp.databases.groups.PageGroupsViewModel
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel
import com.example.alarmmanagerapp.databases.solo.SolosDB
import com.example.alarmmanagerapp.ui.AppColor
import com.example.alarmmanagerapp.ui.ContentPages
import com.example.alarmmanagerapp.ui.PageSettings

class MainActivity : ComponentActivity() {
    private val solosDB by lazy {
        Room.databaseBuilder(
            applicationContext,
            SolosDB::class.java,
            SolosDB.NAME
        ).fallbackToDestructiveMigration().build()
    }
    private val pageSoloViewModel by viewModels<PageSoloViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                @Suppress("UNCHECKED_CAST")
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PageSoloViewModel(solosDB.dao) as T
                }
            }
        }
    )

    private val pageGroupsViewModel by viewModels<PageGroupsViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            window.statusBarColor = AppColor.background.toArgb()
            window.navigationBarColor = AppColor.background.toArgb()
            Surface(
                color = AppColor.background
            ) {
                val navController = rememberNavController()

                NavHost(
                    navController,
                    "Content",
                    Modifier.fillMaxSize()
                ) {
                    composable("Content") {
                        ContentPages(
                            pageSoloViewModel, pageGroupsViewModel
                        ) { navController.navigate("Settings") }
                    }
                    composable("Settings") {
                        PageSettings { navController.navigate("Content") }
                    }
                }
            }
        }
    }
}
