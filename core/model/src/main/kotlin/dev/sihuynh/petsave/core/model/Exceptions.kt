package dev.sihuynh.petsave.core.model

import java.io.IOException

class NoMoreAnimalsException(message: String): Exception(message)

class NetworkUnavailableException(message: String = "No network available") : IOException(message)