package com.enzoroiz.dependencyinjection

import android.util.Log
import javax.inject.Inject

class ServiceProvider @Inject constructor() {
    init {
        Log.e(this.javaClass.simpleName, "ServiceProvider init")
    }
}