package com.enzoroiz.tasktimer.debug

import android.content.ContentResolver
import android.content.ContentValues
import com.enzoroiz.tasktimer.TasksContract
import com.enzoroiz.tasktimer.TimingsContract
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.*
import java.util.GregorianCalendar.DAY_OF_MONTH
import kotlin.math.roundToInt

internal class TestTiming internal constructor(var taskId: Long, date: Long, var duration: Long) {
    var startTime = 0L

    init {
        this.startTime = date / 1000
    }
}
object TestData {

    private const val SECONDS_IN_DAY = 86400
    private const val LOWER_BOUND = 100
    private const val UPPER_BOUND = 500
    private const val MAX_DURATION = SECONDS_IN_DAY / 6

    fun generateData(contentResolver: ContentResolver) {
        val cursor = contentResolver.query(
            TasksContract.CONTENT_URI,
            arrayOf(TasksContract.Columns.ID),
            null,
            null,
            null
        )

        cursor?.let {
            if (cursor.moveToFirst()) {
                do {
                    val taskId = it.getLong(it.getColumnIndex(TasksContract.Columns.ID))

                    val maxCount = LOWER_BOUND + getRandomInt(UPPER_BOUND - LOWER_BOUND)
                    for (i in 0 until maxCount) {
                        val randomDate = getRandomDate()
                        val duration = getRandomInt(MAX_DURATION).toLong()
                        val timing = TestTiming(taskId, randomDate, duration)

                        saveTiming(contentResolver, timing)
                    }
                } while (cursor.moveToNext())
            }
        }

        cursor?.close()
    }

    private fun getRandomInt(max: Int): Int = (Math.random() * max).roundToInt()

    private fun getRandomDate(): Long {
        val startYear = 2019
        val endYear = 2020

        val sec = getRandomInt(59)
        val min = getRandomInt(59)
        val hour = getRandomInt(23)
        val month = getRandomInt(11)
        val year = startYear + (getRandomInt(endYear - startYear))

        val calendar = GregorianCalendar(year, month, 1)
        val day = 1 + getRandomInt(calendar.getActualMaximum(DAY_OF_MONTH) - 1)

        calendar.set(year, month, day, hour, min, sec)
        return calendar.timeInMillis
    }

    private fun saveTiming(contentResolver: ContentResolver, currentTiming: TestTiming) {
        val values = ContentValues()
        values.put(TimingsContract.Columns.TIMING_START_TIME, currentTiming.startTime)
        values.put(TimingsContract.Columns.TIMING_TASK_ID, currentTiming.taskId)
        values.put(TimingsContract.Columns.TIMING_DURATION, currentTiming.duration)

        GlobalScope.launch {
            contentResolver.insert(TimingsContract.CONTENT_URI, values)
        }
    }
}