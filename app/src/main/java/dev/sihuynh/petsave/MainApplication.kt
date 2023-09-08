package dev.sihuynh.petsave

import android.app.Application
import dev.sihuynh.petsave.core.logging.Logger

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Logger.init()
        Logger.d("PetSave Application launched")
    }
}