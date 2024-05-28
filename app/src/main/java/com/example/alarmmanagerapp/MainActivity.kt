package com.example.alarmmanagerapp
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.alarmmanagerapp.ui.NavigationBar
import com.example.alarmmanagerapp.ui.PageSettings
import com.example.alarmmanagerapp.ui.theme.AlarmManagerAppTheme

class MainActivity : ComponentActivity() {
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
                    composable("PageSolo") { PageSolo() }
                    composable("PageGroups") { PageGroups() }
                    composable("PageSettings") { PageSettings() }
                }
            }
        }
    }
}
