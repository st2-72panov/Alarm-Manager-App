package com.example.alarmmanagerapp.databases.solo

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [SoloAlarmEntity::class], version = 1
)
abstract class SolosDB : RoomDatabase() {

    abstract val dao: SolosDao

    companion object {

        const val DB_NAME = "solo"

        fun createDataBase(context: Context): SolosDB {
            return Room.databaseBuilder(
                context, SolosDB::class.java, DB_NAME
            ).build()
        }
    }
}