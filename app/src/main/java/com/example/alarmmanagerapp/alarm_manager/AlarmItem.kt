package com.example.alarmmanagerapp.alarm_manager
import java.time.DayOfWeek
import java.time.LocalTime
import java.util.TreeSet

typealias CombinedID = Int
typealias ID = Short

// data set with everything AlarmSchedule needs to schedule an alarm
data class AlarmItem(
    val combinedID: CombinedID,
    val title: String? = null,
    val time: LocalTime,
    val weekDays: TreeSet<DayOfWeek>
) {
    companion object {
        fun makeCombinedID(
            internalID: ID, groupID: ID
        ): CombinedID {
            return (groupID.toInt() shl 16) or internalID.toInt()
        }

        fun retrieveGroupID(combinedID: CombinedID): ID {
            return (combinedID shr 16).toShort()
        }

        fun retrieveInternalID(combinedID: CombinedID): ID {
            return combinedID.toShort()
        }
    }
}
