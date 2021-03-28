package com.enzoroiz.tasktimer

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

private const val TAG = "AppDatabase"
private const val DATABASE_NAME = "TaskTimer.db"
private const val DATABASE_VERSION = 4

internal class AppDatabase private constructor(context: Context) :
    SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    init {
        Log.d(TAG, "Database being initialized")
    }

    override fun onCreate(db: SQLiteDatabase) {
        Log.d(TAG, "Creating tables")
        // CREATE TABLE Tasks (_id INTEGER PRIMARY KEY NOT NULL, Name TEXT NOT NULL, Description TEXT, SortOrder INTEGER);
        val query = """CREATE TABLE ${TasksContract.TABLE_NAME} (
            ${TasksContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TasksContract.Columns.TASK_NAME} TEXT NOT NULL,
            ${TasksContract.Columns.TASK_DESCRIPTION} TEXT,
            ${TasksContract.Columns.TASK_SORT_ORDER} INTEGER);
            """.replaceIndent(" ")
        db.execSQL(query)

        addTimingsTable(db)
        addCurrentTimingView(db)
        addTasksDurationsView(db)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        Log.d(TAG, "Upgrading tables")
        when (oldVersion) {
            1 -> {
                addTimingsTable(db)
                addCurrentTimingView(db)
                addTasksDurationsView(db)
            }
            2 -> {
                addCurrentTimingView(db)
                addTasksDurationsView(db)
            }
            3 -> {
                addTasksDurationsView(db)
            }
            else -> throw IllegalStateException("Unknown AppDatabase version")
        }
    }

    private fun addTimingsTable(db: SQLiteDatabase) {
        // CREATE TABLE Timings (_id INTEGER PRIMARY KEY NOT NULL, TaskId INTEGER NOT NULL, StartTime INTEGER NOT NULL, Duration INTEGER NOT NULL);
        var query = """CREATE TABLE ${TimingsContract.TABLE_NAME} (
            ${TimingsContract.Columns.ID} INTEGER PRIMARY KEY NOT NULL,
            ${TimingsContract.Columns.TIMING_TASK_ID} INTEGER NOT NULL,
            ${TimingsContract.Columns.TIMING_START_TIME} INTEGER,
            ${TimingsContract.Columns.TIMING_DURATION} INTEGER);
        """.replaceIndent(" ")
        db.execSQL(query)

        // CREATE TRIGGER REMOVE_TIMINGS AFTER DELETE ON Tasks FOR EACH ROW BEGIN DELETE FROM Timings
        // WHERE TaskId = OLD._id;
        // END;
        query = """CREATE TRIGGER REMOVE_TIMINGS
            AFTER DELETE ON ${TasksContract.TABLE_NAME}
            FOR EACH ROW BEGIN
            DELETE FROM ${TimingsContract.TABLE_NAME}
            WHERE ${TimingsContract.Columns.TIMING_TASK_ID} = OLD.${TasksContract.Columns.ID};
            END;
        """.replaceIndent(" ")
        db.execSQL(query)
    }

    private fun addCurrentTimingView(db: SQLiteDatabase) {
//        CREATE VIEW ViewCurrentTiming
//             AS SELECT Timings._id,
//                 Timings.TaskId,
//                 Timings.StartTime,
//                 Tasks.Name
//             FROM Timings
//             JOIN Tasks
//             ON Timings.TaskId = Tasks._id
//             WHERE Timings.Duration = 0
//             ORDER BY Timings.StartTime DESC;
        val query = """CREATE VIEW ${CurrentTimingContract.TABLE_NAME}
            AS SELECT ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.ID},
                ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_TASK_ID},
                ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_START_TIME},
                ${TasksContract.TABLE_NAME}.${TasksContract.Columns.TASK_NAME}
            FROM ${TimingsContract.TABLE_NAME}
            JOIN ${TasksContract.TABLE_NAME}
            ON ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_TASK_ID} = ${TasksContract.TABLE_NAME}.${TasksContract.Columns.ID}
            WHERE ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_DURATION} = 0
            ORDER BY ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_START_TIME} DESC;
        """.replaceIndent(" ")
        db.execSQL(query)
    }

    private fun addTasksDurationsView(db: SQLiteDatabase) {
//        CREATE VIEW ViewTasksDurations AS
//        SELECT Tasks.Name,
//        Tasks.Description,
//        Timings.StartTime,
//        DATE(Timings.StartTime, 'unixepoch', 'localtime') AS StartDate,
//        SUM(Timings.Duration) AS Duration
//        FROM Tasks INNER JOIN TimingsContract ON Tasks._id = Timings.TaskId
//        GROUP BY Tasks._id, StartDate;
        val query = """CREATE VIEW ${TasksDurationsContract.TABLE_NAME} AS
            SELECT ${TasksContract.TABLE_NAME}.${TasksContract.Columns.TASK_NAME},
            ${TasksContract.TABLE_NAME}.${TasksContract.Columns.TASK_DESCRIPTION},
            ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_START_TIME},
            DATE(${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_START_TIME}, 'unixepoch', 'localtime') AS ${TasksDurationsContract.Columns.START_DATE},
            SUM(${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_DURATION}) AS ${TasksDurationsContract.Columns.TIMING_DURATION}
            FROM ${TasksContract.TABLE_NAME} INNER JOIN ${TimingsContract.TABLE_NAME} ON ${TasksContract.TABLE_NAME}.${TasksContract.Columns.ID} = ${TimingsContract.TABLE_NAME}.${TimingsContract.Columns.TIMING_TASK_ID}
            GROUP BY ${TasksContract.TABLE_NAME}.${TasksContract.Columns.ID}, ${TasksDurationsContract.Columns.START_DATE};
        """.replaceIndent(" ")
        db.execSQL(query)
    }

    companion object : SingletonHolder<AppDatabase, Context>(::AppDatabase)
}