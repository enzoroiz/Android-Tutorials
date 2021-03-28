package com.enzoroiz.tasktimer

import java.util.*

class Timing(val taskId: Long, val startTime: Long = Date().time / 1000, var id: Long = 0) {
    var duration: Long = 0
        private set

    fun setDuration() {
        duration = Date().time / 1000 - startTime
    }
}