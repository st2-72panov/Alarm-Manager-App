package com.example.alarmmanagerapp.databases.solo

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface SolosDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertEntity(soloAlarmEntity: SoloAlarmEntity)

    @Delete
    suspend fun deleteEntity(soloAlarmEntity: SoloAlarmEntity)

    @Query("DELETE FROM ${SolosDB.NAME} WHERE id = :id")
    suspend fun deleteEntity(id: Short)

    @Query(
        "SELECT * FROM ${SolosDB.NAME} ORDER BY time ASC, weekDays ASC, title ASC, isOn DESC"
    )
    fun getEntitiesByTime(): Flow<List<SoloAlarmEntity>>

    @Query(
        "SELECT * FROM ${SolosDB.NAME} ORDER BY isOn ASC, time ASC, weekDays ASC, title ASC"
    )
    fun getEntitiesByIsOn(): Flow<List<SoloAlarmEntity>>
}