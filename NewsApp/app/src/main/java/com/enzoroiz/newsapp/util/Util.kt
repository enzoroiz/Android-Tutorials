package com.enzoroiz.newsapp.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

object Util {
    fun isNetworkAvailable(context: Context?): Boolean {
        context?.let {
            val connectivityManager =
                it.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
                    ?.run {
                        return hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                                || hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
                                || hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                    }
            } else {
                connectivityManager.activeNetworkInfo?.let { networkInfo ->
                    return listOf(
                        ConnectivityManager.TYPE_WIFI,
                        ConnectivityManager.TYPE_MOBILE
                    ).contains(networkInfo.type)
                }
            }
        }

        return false
    }
}