package com.example.alarmmanagerapp.alarm_manager
import androidx.core.graphics.toColor
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.LocalTime
import java.util.TreeSet
import kotlin.math.exp
import kotlin.math.pow

typealias CombinedID = Int
typealias ID = Short

// data set with everything AlarmSchedule needs to schedule an alarm
data class AlarmItem(
    val combinedID: CombinedID,
    val title: String? = null,
    val time: LocalTime,
    val weekDays: TreeSet<DayOfWeek>
) {
    private val ID_PLUS_ONE = ID.MAX_VALUE + 1

    fun makeCombinedID(
        internalID: ID, groupID: ID
    ): CombinedID {
        return groupID * ID_PLUS_ONE + internalID
    }

    fun retrieveGroupID(combinedID: CombinedID): ID {
        return (combinedID / ID_PLUS_ONE).toShort()
    }

    fun retrieveInternalID(combinedID: CombinedID): ID {
        return (combinedID % ID_PLUS_ONE).toShort()
    }
}
