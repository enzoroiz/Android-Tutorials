package com.enzoroiz.dependencyinjection

import android.util.Log

class MemoryCard constructor(private val storageSize: Int) {
    init {
        Log.e(this.javaClass.simpleName, "MemoryCard init: $storageSize Gb")
    }
}