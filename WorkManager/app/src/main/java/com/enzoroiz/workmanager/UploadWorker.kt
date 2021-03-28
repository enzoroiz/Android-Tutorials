package com.enzoroiz.workmanager

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import java.lang.Exception

class UploadWorker(context: Context, workerParameters: WorkerParameters): Worker(context, workerParameters) {
    override fun doWork(): Result {
        val uploadCounter = inputData.getInt(MainActivity.UPLOAD_COUNTER_KEY, 1000)
        return try {
            for (i in 0..uploadCounter) {
                Log.e(this.javaClass.simpleName, i.toString())
            }

            Result.success(workDataOf(RESULT_MESSAGE_KEY to RESULT_MESSAGE))
        } catch (e: Exception) {
            Result.failure()
        }
    }

    companion object {
        const val RESULT_MESSAGE_KEY = "result_message_key"
        const val RESULT_MESSAGE = "Work has succeeded!"
    }
}