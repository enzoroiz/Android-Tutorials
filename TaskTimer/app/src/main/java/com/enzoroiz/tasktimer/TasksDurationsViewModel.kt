package com.enzoroiz.tasktimer

import android.app.Application
import android.content.*
import android.database.ContentObserver
import android.database.Cursor
import android.os.Handler
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class TasksDurationsViewModel(application: Application) : AndroidViewModel(application) {
    private var calendar = GregorianCalendar()
    private val sharedPreferences = application.getSharedPreferences(SHARED_PREFERENCES_REFERENCE, Context.MODE_PRIVATE)
    private val databaseCursor = MutableLiveData<Cursor>()
    private val selection = "${TasksDurationsContract.Columns.TIMING_START_TIME} Between ? AND ?"
    private var selectionArgs = emptyArray<String>()

    val cursor: LiveData<Cursor>
        get() = databaseCursor

    var firstDayOfWeek = sharedPreferences.getInt(SHARED_PREFERENCE_SETTINGS_FIRST_DAY, calendar.firstDayOfWeek)
        private set

    var showWeeklyReport = true
        private set

    var sortOrder = SortColumn.NAME
        set(order) {
            if (field != order) {
                field = order
                loadTasksDurations()
            }
        }

    private val contentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            applyReportFilter()
        }
    }

    private val broadcastReceiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            intent?.action.let {
                if (it == Intent.ACTION_TIMEZONE_CHANGED || it == Intent.ACTION_LOCALE_CHANGED) {
                    val currentCalendarTime = calendar.timeInMillis
                    calendar = GregorianCalendar().apply { this.firstDayOfWeek = firstDayOfWeek }
                    calendar.timeInMillis = currentCalendarTime
                    applyReportFilter()
                }
            }
        }
    }

    private val sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            SHARED_PREFERENCE_SETTINGS_FIRST_DAY -> {
                firstDayOfWeek = sharedPreferences.getInt(key, calendar.firstDayOfWeek)
                calendar.firstDayOfWeek = firstDayOfWeek
                applyReportFilter()
            }
        }
    }

    init {
        calendar.firstDayOfWeek = firstDayOfWeek

        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        application.apply {
            contentResolver.registerContentObserver(
                TimingsContract.CONTENT_URI,
                true,
                contentObserver
            )

            registerReceiver(
                broadcastReceiver,
                IntentFilter(Intent.ACTION_TIMEZONE_CHANGED).apply {
                    addAction(Intent.ACTION_LOCALE_CHANGED)
                })
        }

        applyReportFilter()
    }

    override fun onCleared() {
        super.onCleared()
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)

        getApplication<Application>().apply {
            contentResolver.unregisterContentObserver(contentObserver)
            unregisterReceiver(broadcastReceiver)
        }

        databaseCursor.value?.close()
    }

    fun toggleReportFilterPeriod() {
        showWeeklyReport = showWeeklyReport.not()
        applyReportFilter()
    }

    fun getReportFilterDate(): Date {
        return calendar.time
    }

    fun setReportFilterDate(year: Int, month: Int, dayOfMonth: Int) {
        if (calendar.get(GregorianCalendar.YEAR) != year
            || calendar.get(GregorianCalendar.MONTH) != month
            || calendar.get(GregorianCalendar.DAY_OF_MONTH) != dayOfMonth) {
            calendar.set(year, month, dayOfMonth, 0, 0, 0)
            applyReportFilter()
        }
    }

    fun deleteTimings(upToDate: Long) {
        val upToDateParam = (upToDate / 1000).toString()
        val selection = "${TimingsContract.Columns.TIMING_START_TIME} < ?"
        val selectionArgs = arrayOf(upToDateParam)

        viewModelScope.launch(Dispatchers.IO) {
            getApplication<Application>().contentResolver.delete(
                TimingsContract.CONTENT_URI,
                selection,
                selectionArgs
            )
        }
    }

    private fun applyReportFilter() {
        val currentCalendarDate = calendar.timeInMillis

        if (showWeeklyReport) {
            calendar.set(GregorianCalendar.DAY_OF_WEEK, calendar.firstDayOfWeek)
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 0)
            calendar.set(GregorianCalendar.MINUTE, 0)
            calendar.set(GregorianCalendar.SECOND, 0)
            val startDate = calendar.timeInMillis / 1000

            calendar.add(GregorianCalendar.DATE,6)
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 23)
            calendar.set(GregorianCalendar.MINUTE, 59)
            calendar.set(GregorianCalendar.SECOND, 59)
            val endDate = calendar.timeInMillis / 1000

            selectionArgs = arrayOf(startDate.toString(), endDate.toString())
        } else {
            calendar.set(GregorianCalendar.HOUR_OF_DAY, 0)
            calendar.set(GregorianCalendar.MINUTE, 0)
            calendar.set(GregorianCalendar.SECOND, 0)
            val startDate = calendar.timeInMillis / 1000

            calendar.set(GregorianCalendar.HOUR_OF_DAY, 23)
            calendar.set(GregorianCalendar.MINUTE, 59)
            calendar.set(GregorianCalendar.SECOND, 59)
            val endDate = calendar.timeInMillis / 1000

            selectionArgs = arrayOf(startDate.toString(), endDate.toString())
        }

        calendar.timeInMillis = currentCalendarDate
        loadTasksDurations()
    }

    private fun loadTasksDurations() {
        val order = when(sortOrder) {
            SortColumn.NAME -> TasksDurationsContract.Columns.TASK_NAME
            SortColumn.DESCRIPTION -> TasksDurationsContract.Columns.TASK_DESCRIPTION
            SortColumn.START_DATE -> TasksDurationsContract.Columns.START_DATE
            SortColumn.DURATION -> TasksDurationsContract.Columns.TIMING_DURATION
        }

        viewModelScope.launch(Dispatchers.IO) {
            val cursor = getApplication<Application>().contentResolver.query(
                TasksDurationsContract.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                order
            )

            databaseCursor.postValue(cursor)
        }
    }
}

enum class SortColumn {
    NAME,
    DESCRIPTION,
    START_DATE,
    DURATION
}