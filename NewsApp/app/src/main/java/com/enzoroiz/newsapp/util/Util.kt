package com.enzoroiz.newsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.os.Build

class Util {
    fun isNetworkAvailable(context: Context?): Boolean {
        context?.let {
            val connectivityManager = it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                val capabilities = connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            } else {

            }
        }

        return false
    }
}