package com.enzoroiz.dependencyinjection

import android.util.Log
import javax.inject.Inject

class NickelCadmiumBattery @Inject constructor(): Battery {
    init {
        Log.e(this.javaClass.simpleName, "Nickel Cadmium Battery init")
    }

    override fun getPercentage() = "50% battery level"
}