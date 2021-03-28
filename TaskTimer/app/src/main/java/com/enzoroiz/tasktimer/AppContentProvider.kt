package com.enzoroiz.tasktimer

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteQueryBuilder
import android.net.Uri
import android.util.Log
import androidx.core.content.ContentProviderCompat.requireContext

const val CONTENT_AUTHORITY = "com.enzoroiz.tasktimer.provider"
private const val TAG = "AppContentProvider"
private const val TASKS = 100
private const val TASKS_ID = 101
private const val TIMINGS = 200
private const val TIMINGS_ID = 201
private const val CURRENT_TIMING = 300
private const val TASK_DURATIONS = 400

val CONTENT_AUTHORITY_URI: Uri = Uri.parse("content://$CONTENT_AUTHORITY")

class AppContentProvider : ContentProvider() {

    private val uriMatcher by lazy { buildUriMatcher() }

    private fun buildUriMatcher(): UriMatcher {
        Log.d(TAG, "building UriMatcher")
        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(CONTENT_AUTHORITY, TasksContract.TABLE_NAME, TASKS)
        matcher.addURI(CONTENT_AUTHORITY, "${TasksContract.TABLE_NAME}/#", TASKS_ID)

        matcher.addURI(CONTENT_AUTHORITY, TimingsContract.TABLE_NAME, TIMINGS)
        matcher.addURI(CONTENT_AUTHORITY, "${TimingsContract.TABLE_NAME}/#", TIMINGS_ID)

        matcher.addURI(CONTENT_AUTHORITY, CurrentTimingContract.TABLE_NAME, CURRENT_TIMING)

        matcher.addURI(CONTENT_AUTHORITY, TasksDurationsContract.TABLE_NAME, TASK_DURATIONS)

        return matcher
    }

    override fun onCreate(): Boolean {
        Log.d(TAG, "onCreate")
        return true
    }

    override fun getType(uri: Uri): String? {
        return when (uriMatcher.match(uri)) {
            TASKS -> TasksContract.CONTENT_TYPE
            TASKS_ID -> TasksContract.CONTENT_ITEM_TYPE
            TIMINGS -> TimingsContract.CONTENT_TYPE
            TIMINGS_ID -> TimingsContract.CONTENT_ITEM_TYPE
            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }
    }

    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        Log.d(TAG, "query: $uri")
        val match = uriMatcher.match(uri)
        Log.d(TAG, "match is $match")

        val queryBuilder = SQLiteQueryBuilder()
        when (match) {
            TASKS -> queryBuilder.tables = TasksContract.TABLE_NAME

            TASKS_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val taskId = TasksContract.getId(uri)
                queryBuilder.appendWhere("${TasksContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$taskId")
            }

            TIMINGS -> queryBuilder.tables = TimingsContract.TABLE_NAME

            TIMINGS_ID -> {
                queryBuilder.tables = TasksContract.TABLE_NAME
                val timingId = TimingsContract.getId(uri)
                queryBuilder.appendWhere("${TimingsContract.Columns.ID} = ")
                queryBuilder.appendWhereEscapeString("$timingId")
            }

            CURRENT_TIMING -> queryBuilder.tables = CurrentTimingContract.TABLE_NAME

            TASK_DURATIONS -> queryBuilder.tables = TasksDurationsContract.TABLE_NAME

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        val db = AppDatabase.getInstance(requireContext(this)).readableDatabase
        val cursor =
            queryBuilder.query(db, projection, selection, selectionArgs, null, null, sortOrder)
        Log.d(TAG, "Cursor returned ${cursor.count} rows")

        return cursor
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        Log.d(TAG, "query: $uri")
        val match = uriMatcher.match(uri)
        var returnUri: Uri? = null
        var recordId = 0L
        Log.d(TAG, "match is $match")

        values?.let {

            when (match) {
                TASKS -> {
                    val db = getWriteableDatabase()
                    recordId = db.insert(TasksContract.TABLE_NAME, null, it)
                    if (recordId == -1L) throw SQLException("Failed to insert: $uri")

                    returnUri = TasksContract.buildUriFromId(recordId)
                }

                TIMINGS -> {
                    val db = getWriteableDatabase()
                    recordId = db.insert(TimingsContract.TABLE_NAME, null, it)
                    if (recordId == -1L) throw SQLException("Failed to insert: $uri")

                    returnUri = TimingsContract.buildUriFromId(recordId)
                }

                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }

        if (recordId > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }

        return returnUri
    }

    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        Log.d(TAG, "query: $uri")
        val match = uriMatcher.match(uri)
        var count = 0
        Log.d(TAG, "match is $match")

        var updateSelect: String

        values?.let {
            when (match) {
                TASKS -> {
                    val db = getWriteableDatabase()
                    count = db.update(TasksContract.TABLE_NAME, values, selection, selectionArgs)
                }

                TASKS_ID -> {
                    val db = getWriteableDatabase()
                    val id = TasksContract.getId(uri)
                    updateSelect = "${TasksContract.Columns.ID} = $id"

                    if (selection.isNullOrEmpty().not()) {
                        updateSelect += "AND ($selection)"
                    }

                    count = db.update(TasksContract.TABLE_NAME, values, updateSelect, selectionArgs)
                }

                TIMINGS -> {
                    val db = getWriteableDatabase()
                    count = db.update(TimingsContract.TABLE_NAME, values, selection, selectionArgs)
                }

                TIMINGS_ID -> {
                    val db = getWriteableDatabase()
                    val id = TimingsContract.getId(uri)
                    updateSelect = "${TimingsContract.Columns.ID} = $id"

                    if (selection.isNullOrEmpty().not()) {
                        updateSelect += "AND ($selection)"
                    }

                    count = db.update(TimingsContract.TABLE_NAME, values, updateSelect, selectionArgs)
                }

                else -> throw IllegalArgumentException("Unknown URI: $uri")
            }
        }

        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }

        return count
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        Log.d(TAG, "query: $uri")
        val match = uriMatcher.match(uri)
        val count: Int
        Log.d(TAG, "match is $match")

        var deleteSelect: String

        when (match) {
            TASKS -> {
                val db = getWriteableDatabase()
                count = db.delete(TasksContract.TABLE_NAME, selection, selectionArgs)
            }

            TASKS_ID -> {
                val db = getWriteableDatabase()
                val id = TasksContract.getId(uri)
                deleteSelect = "${TasksContract.Columns.ID} = $id"

                if (selection.isNullOrEmpty().not()) {
                    deleteSelect += "AND ($selection)"
                }

                count = db.delete(TasksContract.TABLE_NAME, deleteSelect, selectionArgs)
            }

            TIMINGS -> {
                val db = getWriteableDatabase()
                count = db.delete(TimingsContract.TABLE_NAME, selection, selectionArgs)
            }

            TIMINGS_ID -> {
                val db = getWriteableDatabase()
                val id = TimingsContract.getId(uri)
                deleteSelect = "${TimingsContract.Columns.ID} = $id"

                if (selection.isNullOrEmpty().not()) {
                    deleteSelect += "AND ($selection)"
                }

                Log.d(TAG, deleteSelect)

                count = db.delete(TimingsContract.TABLE_NAME, deleteSelect, selectionArgs)
            }

            else -> throw IllegalArgumentException("Unknown URI: $uri")
        }

        if (count > 0) {
            context?.contentResolver?.notifyChange(uri, null)
        }

        return count
    }

    private fun getWriteableDatabase() =
        AppDatabase.getInstance(requireContext(this)).writableDatabase

}