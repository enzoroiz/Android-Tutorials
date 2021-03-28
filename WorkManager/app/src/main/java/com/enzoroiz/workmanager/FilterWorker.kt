package com.enzoroiz.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.lang.Exception

class FilterWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        return try {
            for (i in 0..400) {
                Log.e(this.javaClass.simpleName, i.toString())
            }

            Result.success()
        } catch (e: Exception) {
            Result.failure()
        }
    }
}