package com.example.alarmmanagerapp.databases.solo

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.alarmmanagerapp.util.Converter

@Database(entities = [SoloAlarmEntity::class], version = 1)
@TypeConverters(Converter::class)
abstract class SolosDB : RoomDatabase() {
    abstract val dao: SolosDao

    companion object {
        const val NAME = "solo"
    }
}