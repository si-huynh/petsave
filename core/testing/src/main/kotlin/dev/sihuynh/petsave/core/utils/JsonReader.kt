package dev.sihuynh.petsave.core.utils

import androidx.test.platform.app.InstrumentationRegistry
import java.io.IOException
import java.io.InputStream

object JsonReader {
    fun getJson(path: String): String {
        return try {
            val context = InstrumentationRegistry.getInstrumentation().context
            val jsonStream: InputStream = context.assets.open(path)
            String(jsonStream.readBytes())
        } catch (exception: IOException) {
            throw exception
        }
    }
}