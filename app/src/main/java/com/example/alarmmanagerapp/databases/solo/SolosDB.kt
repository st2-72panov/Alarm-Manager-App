package com.example.alarmmanagerapp.databases.solo
import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [SoloAlarmEntity::class],
    version = 1
)
abstract class SolosDB : RoomDatabase() {
    abstract val dao: SolosDao
    companion object {
        const val NAME = "solo"
//        private var INSTANCE: SolosDB? = null
//
//        fun getInstance(context: Context): SolosDB {
//            synchronized(this) {
//                var instance = INSTANCE
//                if (instance == null) {
//                    instance = Room.databaseBuilder(
//                        context.applicationContext,
//                        SolosDB::class.java,
//                        NAME
//                    ).fallbackToDestructiveMigration().build()
//                    INSTANCE = instance
//                }
//                return instance
//            }
//        }
    }
}