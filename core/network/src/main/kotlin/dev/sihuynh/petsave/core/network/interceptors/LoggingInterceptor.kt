package dev.sihuynh.petsave.core.network.interceptors

import dev.sihuynh.petsave.core.logging.Logger
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Inject

class LoggingInterceptor @Inject constructor(): HttpLoggingInterceptor.Logger {
    override fun log(message: String) {
        Logger.i(message)
    }
}