package com.example.alarmmanagerapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.room.Room
import com.example.alarmmanagerapp.databases.solo.PageSoloViewModel
import com.example.alarmmanagerapp.databases.solo.SolosDB
import com.example.alarmmanagerapp.ui.NavigationBar
import com.example.alarmmanagerapp.ui.PageSettings
import com.example.alarmmanagerapp.ui.theme.AlarmManagerAppTheme

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
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    return PageSoloViewModel(solosDB.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AlarmManagerAppTheme {
                val navController = rememberNavController()

                NavigationBar()

                NavHost(
                    navController,
                    "PageSolo",
                    modifier = Modifier.fillMaxSize()
                ) {
                    composable("PageSolo") { PageSolo(pageSoloViewModel) }
                    composable("PageGroups") { PageGroups() }
                    composable("PageSettings") { PageSettings() }
                }
            }
        }
    }
}
