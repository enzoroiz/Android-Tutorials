package com.enzoroiz.dependencyinjection

object SmartPhoneInjector {
    lateinit var component: SmartPhoneComponent
        private set

    fun init() {
        component = DaggerSmartPhoneComponent.builder()
            .memoryCardModule(MemoryCardModule(128))
            .build()
    }
}