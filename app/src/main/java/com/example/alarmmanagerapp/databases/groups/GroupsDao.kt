package com.example.alarmmanagerapp.databases.groups
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface GroupsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertItem(groupAlarmEntity: GroupAlarmEntity)

    @Delete
    suspend fun deleteItem(groupAlarmEntity: GroupAlarmEntity)

    @Query("SELECT * FROM ${GroupsDB.DB_NAME}")
    suspend fun getAllItems(): List<GroupAlarmEntity>
}