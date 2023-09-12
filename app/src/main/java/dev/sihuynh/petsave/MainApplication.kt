package dev.sihuynh.petsave

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import dev.sihuynh.petsave.core.logging.Logger

@HiltAndroidApp
class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init()
        Logger.d("PetSave Application launched")
    }
}