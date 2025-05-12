package com.example.alarmmanagerapp.alarm_display

import android.media.RingtoneManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

class AlarmScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setShowWhenLocked(true)
        setTurnScreenOn(true)

        val ringtoneManager = RingtoneManager(this)
        ringtoneManager.setType(RingtoneManager.TYPE_ALARM)
        val cursor = ringtoneManager.cursor
        cursor.moveToFirst()
        val ringtoneURI = ringtoneManager.getRingtoneUri(cursor.position)
        val ringtone = RingtoneManager.getRingtone(this, ringtoneURI)
        ringtone.play()

        setContent {
            Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                var counter by remember {
                    mutableIntStateOf(0)
                }

                Column(Modifier.fillMaxSize()) {
                    Text("Oh, hi! $counter")
                    Button(
                        modifier = Modifier.width(100.dp).height(100.dp),
                        onClick = { counter++ }
                    ) {
                        Text("Click me")
                    }
                }
            }
        }
    }
}