package dev.sihuynh.petsave.core.network.utils

import androidx.test.platform.app.InstrumentationRegistry
import dev.sihuynh.petsave.core.logging.Logger
import java.io.IOException
import java.io.InputStream

object JsonReader {
    fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open(path)
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            Logger.e("Error reading network response json asset", exception)
            throw exception
        }
    }
}