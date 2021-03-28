package com.enzoroiz.dependencyinjection

import dagger.Module
import dagger.Provides

@Module
class MemoryCardModule(private val storageSize: Int) {
    @Provides
    fun providesMemoryCard(): MemoryCard {
        return MemoryCard(storageSize)
    }
}