package com.example.alarmmanagerapp.databases.groups
import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(
    entities = [GroupAlarmEntity::class],
    version = 1
)
abstract class GroupsDB: RoomDatabase() {

    abstract val dao: GroupsDao

    companion object {

        const val DB_NAME = "group"

        fun createDataBase(context: Context): GroupsDB {
            return Room.databaseBuilder(
                context,
                GroupsDB::class.java,
                DB_NAME
            ).build()
        }
    }
}