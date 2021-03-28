package com.enzoroiz.dependencyinjection

import androidx.appcompat.app.AppCompatActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [MemoryCardModule::class, NickelCadmiumBatteryModule::class])
interface SmartPhoneComponent {
    fun inject(activity: MainActivity)
}