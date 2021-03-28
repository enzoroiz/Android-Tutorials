package com.enzoroiz.top10downloader

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.lang.Exception
import java.lang.StringBuilder
import java.net.HttpURLConnection
import java.net.MalformedURLException
import java.net.URL

private const val TAG = "DownloadData"
class DownloadData(private val onDataAvailable: (List<FeedEntry>) -> Unit) : AsyncTask<String, Void, String>() {

    override fun doInBackground(vararg url: String): String {
        val rssFeed = downloadLightXML(url[0])
        Log.d(TAG, "Downloading your data...")
        if (rssFeed.isEmpty()) Log.e(TAG, "doInBackground: Error downloading")
        return rssFeed
    }

    override fun onPostExecute(result: String) {
        val parser = XMLApplicationsParser()
        if (result.isNotEmpty()) { parser.parse(result) }

        onDataAvailable(parser.applications)
    }

    private fun downloadLightXML(urlPath: String): String {
        try {
            return URL(urlPath).readText()
        } catch (e: MalformedURLException) {
            Log.e(TAG, "MalformedURLException: ${e.message}")
        } catch (e: IOException) {
            Log.e(TAG, "IOException: ${e.message}")
        } catch (e: SecurityException) {
            Log.e(TAG, "Security Exception: ${e.message}")
        }

        return ""
    }

    private fun downloadXML(urlPath: String?): String {
        val xmlResult = StringBuilder()
        try {
            val url = URL(urlPath)
            val connection = url.openConnection() as HttpURLConnection

            connection.inputStream.bufferedReader().use {
                xmlResult.append(it.readText())
            }

            return xmlResult.toString()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> "downloadXML: Invalid URL ${e.message}"
                is IOException -> "downloadXML: IO Exception reading data ${e.message}"
                is SecurityException -> "downloadXML: Security exception. Needs permission? ${e.message}"
                else -> "downloadXML: Unknown exception"
            }

            Log.e(TAG, errorMessage)
        }

        return ""
    }
}