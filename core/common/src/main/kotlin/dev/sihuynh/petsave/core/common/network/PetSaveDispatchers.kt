package dev.sihuynh.petsave.core.common.network

import javax.inject.Qualifier

enum class PetSaveDispatchers {
    Default,
    IO
}

@Qualifier
@Retention
annotation class Dispatcher(@Suppress("unused") val petsaveDispatcher: PetSaveDispatchers)