package com.enzoroiz.tasktimer

import android.net.Uri

object TasksDurationsContract {
    internal const val TABLE_NAME = "ViewTaskDurations"

    val CONTENT_URI: Uri = Uri.withAppendedPath(CONTENT_AUTHORITY_URI, TABLE_NAME)

    object Columns {
        const val TASK_NAME = TasksContract.Columns.TASK_NAME
        const val TASK_DESCRIPTION = TasksContract.Columns.TASK_DESCRIPTION
        const val TIMING_START_TIME = TimingsContract.Columns.TIMING_START_TIME
        const val TIMING_DURATION = TimingsContract.Columns.TIMING_DURATION
        const val START_DATE = "StartDate"
    }
}