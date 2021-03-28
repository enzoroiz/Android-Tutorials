package com.enzoroiz.tasktimer

import android.app.Application
import android.content.ContentValues
import android.content.Context
import android.content.SharedPreferences
import android.database.ContentObserver
import android.database.Cursor
import android.os.Handler
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.enzoroiz.tasktimer.TasksContract.getId
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class TaskTimerViewModel(application: Application) : AndroidViewModel(application) {
    private var currentTiming: Timing? = null
    private val databaseCursor = MutableLiveData<Cursor>()
    private val sharedPreferences = application.getSharedPreferences(SHARED_PREFERENCES_REFERENCE, Context.MODE_PRIVATE)
    private var ignoreTimingsLessThan = sharedPreferences.getInt(SHARED_PREFERENCE_SETTINGS_IGNORE_TIMINGS_LESS_THAN, IGNORE_TIMINGS_LESS_THAN)
    var taskBeingEditedId = 0L
        private set

    val cursor: LiveData<Cursor>
        get() = databaseCursor

    private val currentTimedTaskLiveData = MutableLiveData<String>()
    val timedTaskLiveData: LiveData<String>
        get() = currentTimedTaskLiveData

    private val contentObserver = object : ContentObserver(Handler()) {
        override fun onChange(selfChange: Boolean) {
            loadTasks()
        }
    }

    private val sharedPreferencesListener = SharedPreferences.OnSharedPreferenceChangeListener { sharedPreferences, key ->
        when (key) {
            SHARED_PREFERENCE_SETTINGS_IGNORE_TIMINGS_LESS_THAN -> {
                ignoreTimingsLessThan = sharedPreferences.getInt(key, IGNORE_TIMINGS_LESS_THAN)
            }
        }
    }

    init {
        sharedPreferences.registerOnSharedPreferenceChangeListener(sharedPreferencesListener)

        application.contentResolver.registerContentObserver(
            TasksContract.CONTENT_URI,
            true,
            contentObserver
        )

        currentTiming = getCurrentTimedTask()
        loadTasks()
    }

    override fun onCleared() {
        super.onCleared()
        getApplication<Application>().contentResolver.unregisterContentObserver(contentObserver)
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(sharedPreferencesListener)
        databaseCursor.value?.close()
    }

    fun saveTask(task: Task): Task {
        if (task.name.isNotEmpty()) {
            val values = ContentValues().apply {
                put(TasksContract.Columns.TASK_SORT_ORDER, task.sortOrder)
                put(TasksContract.Columns.TASK_DESCRIPTION, task.description)
                put(TasksContract.Columns.TASK_NAME, task.name)
            }

            if (task.id == 0L) {
                viewModelScope.launch(Dispatchers.IO) {
                    val uri =
                        getApplication<Application>().contentResolver.insert(TasksContract.CONTENT_URI, values)
                    uri?.let { task.id = getId(uri) }
                }
            } else {
                viewModelScope.launch(Dispatchers.IO) {
                    getApplication<Application>().contentResolver.update(
                        TasksContract.buildUriFromId(task.id),
                        values,
                        null,
                        null
                    )
                }
            }
        }

        return task
    }

    fun deleteTask(taskId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            getApplication<Application>().contentResolver.delete(TasksContract.buildUriFromId(taskId), null, null)
        }

        currentTiming?.taskId.let {
            if (it == taskId) {
                currentTiming = null
                currentTimedTaskLiveData.value = null
            }
        }
    }

    fun startTiming(task: Task) {
        val timing = currentTiming

        timing?.let {
            it.setDuration()
            saveTiming(it)

            if (task.id != timing.taskId) {
                currentTiming = Timing(task.id)
                saveTiming(currentTiming!!)
            } else {
                currentTiming = null
            }
        } ?: let {
            currentTiming = Timing(task.id)
            saveTiming(currentTiming!!)
        }

        currentTimedTaskLiveData.value = currentTiming?.let { task.name }
    }

    fun startEditingTask(taskId: Long) {
        taskBeingEditedId = taskId
    }

    fun stopEditingTask() {
        taskBeingEditedId = 0L
    }

    private fun saveTiming(currentTiming: Timing) {
        val inserting = currentTiming.duration == 0L
        val values = ContentValues().apply {
            put(TimingsContract.Columns.TIMING_DURATION, currentTiming.duration)
            if (inserting) {
                put(TimingsContract.Columns.TIMING_TASK_ID, currentTiming.taskId)
                put(TimingsContract.Columns.TIMING_START_TIME, currentTiming.startTime)
            }
        }

        viewModelScope.launch(Dispatchers.IO) {
            if (inserting) {
                getApplication<Application>().contentResolver.insert(
                    TimingsContract.CONTENT_URI,
                    values
                )?.let {
                    currentTiming.id = TimingsContract.getId(it)
                }
            } else {
                if (currentTiming.duration >= ignoreTimingsLessThan) {
                    Log.d("TaskTimerVM", "Saving")
                    getApplication<Application>().contentResolver.update(
                        TimingsContract.buildUriFromId(
                            currentTiming.id
                        ), values, null, null
                    )
                } else {
                    Log.d("TaskTimerVM", "Ignoring")
                    getApplication<Application>().contentResolver.delete(
                        TimingsContract.buildUriFromId(currentTiming.id),
                        null,
                        null
                    )
                }
            }
        }
    }

    private fun loadTasks() {
        val sortOrder = "${TasksContract.Columns.TASK_SORT_ORDER}, ${TasksContract.Columns.TASK_NAME}"
        val projection = arrayOf(
            TasksContract.Columns.ID,
            TasksContract.Columns.TASK_NAME,
            TasksContract.Columns.TASK_DESCRIPTION,
            TasksContract.Columns.TASK_SORT_ORDER
        )

        viewModelScope.launch(Dispatchers.IO) {
            val cursor = getApplication<Application>().contentResolver.query(
                TasksContract.CONTENT_URI,
                projection,
                null,
                null,
                sortOrder
            )

            databaseCursor.postValue(cursor)
        }
    }

    private fun getCurrentTimedTask() : Timing? {
        var timing: Timing? = null

        val cursor = getApplication<Application>().contentResolver.query(
            CurrentTimingContract.CONTENT_URI,
            null,
            null,
            null,
            null
        )

        cursor?.let {
            if (it.moveToFirst()) {
                val id = it.getLong(it.getColumnIndex(CurrentTimingContract.Columns.TIMING_ID))
                val taskId = it.getLong(it.getColumnIndex(CurrentTimingContract.Columns.TASK_ID))
                val taskName = it.getString(it.getColumnIndex(CurrentTimingContract.Columns.TASK_NAME))
                val startTime = it.getLong(it.getColumnIndex(CurrentTimingContract.Columns.TIMING_START_TIME))

                currentTimedTaskLiveData.value = taskName
                timing = Timing(taskId, startTime, id)
            }
        }

        cursor?.close()

        return timing
    }
}