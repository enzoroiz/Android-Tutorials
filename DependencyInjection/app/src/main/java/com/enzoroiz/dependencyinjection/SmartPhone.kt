package com.enzoroiz.dependencyinjection

import android.util.Log
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SmartPhone @Inject constructor(private val battery: Battery, memoryCard: MemoryCard, simCard: SimCard) {
    init {
        Log.e(this.javaClass.simpleName, "SmartPhone init")
    }

    fun makeCall() {
        Log.e(this.javaClass.simpleName, "Calling...")
    }

    fun getBatteryLevel() {
        Log.e(this.javaClass.simpleName, battery.getPercentage())
    }
}