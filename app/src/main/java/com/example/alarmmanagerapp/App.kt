package com.example.alarmmanagerapp
import android.app.Application
import com.example.alarmmanagerapp.databases.groups.GroupsDB
import com.example.alarmmanagerapp.databases.solo.SolosDB

class App: Application() {
    val solosDB by lazy { SolosDB.createDataBase(this) }
    val groupsDB by lazy { GroupsDB.createDataBase(this) }
}