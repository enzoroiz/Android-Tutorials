package com.enzoroiz.dependencyinjection

import dagger.Binds
import dagger.Module

@Module
abstract class NickelCadmiumBatteryModule {
    @Binds
    abstract fun providesNickelCadmiumBattery(nickelCadmiumBattery: NickelCadmiumBattery): Battery
}