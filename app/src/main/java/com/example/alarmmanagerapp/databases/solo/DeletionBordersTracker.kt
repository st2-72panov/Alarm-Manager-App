package com.example.alarmmanagerapp.databases.solo

typealias Index = Int

class DeletionBordersTracker {
    var leftBorder: Index? = null  // starting border
        private set
    var rightBorder: Index? = null // ending border
        private set

    fun insertNewBorder(newBorder: Index) {
        if (newBorder == leftBorder ||
            newBorder == rightBorder ||
            leftBorder!! < newBorder && newBorder < rightBorder!!
        ) throw IllegalAccessError()

        if (leftBorder == null) {
            leftBorder = newBorder
            rightBorder = newBorder
        } else if (newBorder < leftBorder!!)
            leftBorder = newBorder
        else
            rightBorder = newBorder
    }

    fun replaceLeftBorder(newLeftBorder: Index) {
        if (leftBorder == null)
            throw IllegalAccessError()
        else if (newLeftBorder >= leftBorder!!)
            throw InternalError("$newLeftBorder >= $leftBorder")

        leftBorder = newLeftBorder
    }

    fun replaceRightBorder(newRightBorder: Index) {
        if (rightBorder == null)
            throw IllegalAccessError()
        else if (newRightBorder <= leftBorder!!)
            throw InternalError("$newRightBorder <= $leftBorder")

        rightBorder = newRightBorder
    }

    // functions for removing
    ////////////////////////////////////
    fun removeLeftBorder() {
        if (leftBorder == null)
            throw IllegalAccessError()
        leftBorder = rightBorder
    }

    fun removeRightBorder() {
        if (rightBorder == null)
            throw IllegalAccessError()
        rightBorder = leftBorder
    }
}