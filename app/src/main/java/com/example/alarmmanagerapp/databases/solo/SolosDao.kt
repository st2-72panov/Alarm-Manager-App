package com.example.alarmmanagerapp.databases.solo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SolosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(soloAlarmEntity: SoloAlarmEntity)

    @Delete
    suspend fun deleteItem(soloAlarmEntity: SoloAlarmEntity)

    @Query("SELECT * FROM ${SolosDB.DB_NAME}")
    suspend fun getAllItems(): List<SoloAlarmEntity>
}