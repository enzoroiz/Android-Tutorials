package com.enzoroiz.dependencyinjection

import android.util.Log
import javax.inject.Inject

class SimCard @Inject constructor(serviceProvider: ServiceProvider) {
    init {
        Log.e(this.javaClass.simpleName, "SimCard init")
    }
}