package dev.sihuynh.petsave.core.model.user

data class Token(
    val value: String,
    val expiresIn: Long,
    val type: String,
)
