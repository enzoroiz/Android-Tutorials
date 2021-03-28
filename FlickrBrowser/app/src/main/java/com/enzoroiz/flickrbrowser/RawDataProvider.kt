package com.enzoroiz.flickrbrowser

import android.os.AsyncTask
import java.io.IOException
import java.lang.Exception
import java.net.MalformedURLException
import java.net.URL

enum class DownloadStatus {
    OK,
    IDLE,
    NOT_INITIALISED,
    FAILED_OR_EMPTY,
    PERMISSIONS_ERROR,
    ERROR
}

class RawDataProvider(val listener: OnDownloadCompleted) : AsyncTask<String, Void, String>() {
    private var downloadStatus = DownloadStatus.IDLE

    override fun onPostExecute(result: String) {
        listener.onDownloadCompleted(result, downloadStatus)
    }

    override fun doInBackground(vararg params: String?): String {
        if (params[0] == null) {
            downloadStatus = DownloadStatus.NOT_INITIALISED
            return "No URL specified"
        }

        return try {
            downloadStatus = DownloadStatus.OK
            URL(params[0]).readText()
        } catch (e: Exception) {
            when (e) {
                is MalformedURLException -> downloadStatus = DownloadStatus.NOT_INITIALISED
                is IOException -> downloadStatus = DownloadStatus.FAILED_OR_EMPTY
                is SecurityException -> downloadStatus = DownloadStatus.PERMISSIONS_ERROR
                else -> downloadStatus = DownloadStatus.ERROR
            }

            e.message.toString()
        }
    }

    interface OnDownloadCompleted {
        fun onDownloadCompleted(data:  String, status: DownloadStatus)
    }
}