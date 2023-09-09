package dev.sihuynh.petsave.core.model.user

import kotlinx.datetime.Instant

data class Token(
    val value: String,
    val expiresIn: Long,
    val expiresAt: Instant,
    val type: String,
)
