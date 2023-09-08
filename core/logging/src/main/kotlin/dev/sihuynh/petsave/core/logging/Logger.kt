package dev.sihuynh.petsave.core.logging

import timber.log.Timber

object Logger {
    private val logger by lazy { TimberLogging() }

    fun init() {
        Timber.plant(logger)
    }

    fun d(message: String, t: Throwable? = null) = logger.d(t, message)

    fun i(message: String, t: Throwable? = null) = logger.i(t, message)

    fun e(message: String, t: Throwable? = null) = logger.e(t, message)

    fun wtf(message: String, t: Throwable? = null) = logger.wtf(t, message)
}